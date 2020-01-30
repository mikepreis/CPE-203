import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Miner_Not_Full implements EntityInterface, ActionInterface, MinerInterface {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;


    public Miner_Not_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
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

    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, ActivityClass.createActivityAction(this, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, AnimationClass.createAnimationAction(this, 0), getAnimationPeriod());
    }

    public void executeMinerNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<EntityInterface> notFullTarget = world.findNearest(this.position, Ore.class);

        if (!notFullTarget.isPresent() || !moveToNotFull(this, world, notFullTarget.get(), scheduler) || !transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this, ActivityClass.createActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    public static boolean moveToNotFull(EntityInterface miner, WorldModel world, EntityInterface target, EventScheduler scheduler)
    {
        if (miner.getPosition().adjacent(target.getPosition()))
        {
            miner.resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

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


    public static Miner_Not_Full createMinerNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Miner_Not_Full(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.resourceCount >= this.resourceLimit)
        {
            Miner_Full miner = Miner_Full.createMinerFull(this.id, this.resourceLimit, this.position, this.actionPeriod, this.animationPeriod, this.images);

            world.removeEntity( this);
            scheduler.unscheduleAllEvents( this);

            world.addEntity( miner);
            scheduleActions( scheduler, world, imageStore);

            return true;
        }

        return false;
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


}
