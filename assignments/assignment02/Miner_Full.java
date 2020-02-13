import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Miner_Full implements EntityInterface, Activity, Animation {

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

    public Miner_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;

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
        scheduler.scheduleEvent( this, createAnimationAction(0), getAnimationPeriod());
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler);
        executeAnimationAction(scheduler);
    }

    @Override
    public void executeActivityAction(EventScheduler scheduler) {
        executeMinerFullActivity(this.world, this.imageStore, scheduler);
    }

    public void executeAnimationAction(EventScheduler scheduler)
    {
        this.nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent( this, createAnimationAction(Math.max(this.repeatCount - 1, 0)), this.getAnimationPeriod());
        }
    }

    public void executeMinerFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), Blacksmith.class);

        if (fullTarget.isPresent() && moveToFull(this, world, fullTarget.get(), scheduler))
        {
            transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }

    public ActionClass createActivityAction(WorldModel world, ImageStore imageStore)
    {
        return new ActionClass(this, world, imageStore, 0);
    }

    public ActionClass createAnimationAction(int repeatCount)
    {
        return new ActionClass(this, null, null, repeatCount);
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        Entity miner = Miner_Not_Full.createMinerNotFull(this.getId(), this.getResourceLimit(),
                this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
                this.getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents( this);

        world.addEntity( miner);
        scheduleActions( scheduler, world, imageStore);
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

    public boolean moveToFull(Entity miner, WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (miner.position.adjacent(target.position))
        {
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

    public static Entity createMinerFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Entity(id, position, images, resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }

}