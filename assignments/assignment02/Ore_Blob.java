import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Ore_Blob implements EntityInterface {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    public Ore_Blob(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public List<PImage> getImages() {
        return images;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public int getActionPeriod() {
        return actionPeriod;
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent( this,
                ActivityClass.createActivityAction(this, world, imageStore),
                this.actionPeriod);
        scheduler.scheduleEvent(this,
                AnimationClass.createAnimationAction(this, 0), getAnimationPeriod());
    }


    public boolean moveToOreBlob(WorldModel world, EntityInterface target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPositionOreBlob(world, target.getPosition());

            if (!this.position.equals(nextPos))
            {
                Optional<EntityInterface> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public Point nextPositionOreBlob(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - this.position.getX());
        Point newPos = new Point(this.position.getX() + horiz,
                this.position.getY());

        Optional<EntityInterface> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get().getClass() == Ore.class)))
        {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.position.getX(), this.position.getY() + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.get().getClass() == Ore.class)))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }

    public static Ore_Blob createOreBlob(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Ore_Blob(id, position, images, 0, 0, actionPeriod, animationPeriod);
    }

    public void executeOreBlobActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<EntityInterface> blobTarget = world.findNearest(this.position, Vein.class);
        long nextPeriod = this.actionPeriod;

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().getPosition();

            if (moveToOreBlob(world, blobTarget.get(), scheduler))
            {
                Quake quake = Quake.createQuake(tgtPos, imageStore.getImageList(VirtualWorld.QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.actionPeriod;
                scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, ActivityClass.createActivityAction(this, world, imageStore), nextPeriod);
    }
}
