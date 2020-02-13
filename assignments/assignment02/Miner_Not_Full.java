import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Miner_Not_Full implements EntityInterface, Activity, Animation {

    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;
    public static final Random rand = new Random();

    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Miner_Not_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {

        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;

    }

    public Miner_Not_Full(WorldModel world, ImageStore imageStore, int repeatCount) {
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public List<PImage> getImages() {
        return images;
    }

    @Override
    public int getImageIndex() {
        return imageIndex;
    }

    @Override
    public int getResourceLimit() {
        return resourceLimit;
    }

    @Override
    public int getResourceCount() {
        return resourceCount;
    }

    @Override
    public int getActionPeriod() {
        return actionPeriod;
    }

    @Override
    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, createAnimationAction(0), getAnimationPeriod());
    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore)
    {
        return new Miner_Not_Full( world, imageStore, 0);
    }

    public ActionClass createAnimationAction(int repeatCount)
    {
        return new ActionClass(this, null, null, repeatCount);
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler);
        executeAnimationAction(scheduler);
    }

    @Override
    public void executeActivityAction(EventScheduler scheduler) {
        executeMinerNotFullActivity(this.world, this.imageStore, scheduler);
    }

    @Override
    public void executeAnimationAction(EventScheduler scheduler) {
        this.nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent( this, createAnimationAction(Math.max(this.repeatCount - 1, 0)), this.getAnimationPeriod());
        }
    }


    public void executeMinerNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(this.getPosition(), Ore.class);

        if (!notFullTarget.isPresent() || !moveToNotFull(this, world, notFullTarget.get(), scheduler) || !transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this, createActivityAction( world, imageStore), this.getActionPeriod());
        }
    }

    public boolean moveToNotFull(Entity miner, WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (miner.position.adjacent(target.position))
        {
            miner.resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = nextPositionMiner(world, target.position);

            if (!miner.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(miner, nextPos);
            }
            return false;
        }
    }

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.getResourceCount() >= this.getResourceLimit())
        {
            Entity miner = Miner_Full.createMinerFull(this.getId(), this.getResourceLimit(), this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(), this.getImages());

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
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz,
                this.getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(),
                    this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied( newPos))
            {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }

    public static Entity createMinerNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Entity(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }
}