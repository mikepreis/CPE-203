import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Miner_Full implements EntityInterface {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;


    public Miner_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
    {
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

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, ActivityClass.createActivityAction(this, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent( this, AnimationClass.createAnimationAction(this, 0), getAnimationPeriod());
    }

    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public void executeMinerFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<EntityInterface> fullTarget = world.findNearest(this.position, Blacksmith.class);

        if (fullTarget.isPresent() && moveToFull(this, world, fullTarget.get(), scheduler))
        {
            transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this, ActivityClass.createActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    public static Miner_Full createMinerFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Miner_Full(id, position, images, resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        EntityInterface miner = Miner_Not_Full.createMinerNotFull(this.id, this.resourceLimit,
                this.position, this.actionPeriod, this.animationPeriod,
                this.images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents( this);

        world.addEntity( miner);
        scheduleActions( scheduler, world, imageStore);
    }

    public Point nextPositionMiner(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - this.position.getX());
        Point newPos = new Point(this.position.getX() + horiz,
                this.position.getY());

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.position.getX(),
                    this.position.getY() + vert);

            if (vert == 0 || world.isOccupied( newPos))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }

    public static boolean moveToFull(Miner_Full miner, WorldModel world, EntityInterface target, EventScheduler scheduler)
    {
        if (miner.getPosition().adjacent(target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = miner.nextPositionMiner(world, target.getPosition());

            if (!miner.getPosition().equals(nextPos))
            {
                Optional<EntityInterface> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(miner, nextPos);
            }
            return false;
        }
    }

}
