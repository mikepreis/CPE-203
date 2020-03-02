import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class MinerNotFull extends Entity implements AnimationInterface {

    private static final int MINER_NUM_PROPERTIES = 7;
    private static final int MINER_ID = 1;
    private static final int MINER_COL = 2;
    private static final int MINER_ROW = 3;
    private static final int MINER_LIMIT = 4;
    private static final int MINER_ACTION_PERIOD = 5;
    private static final int MINER_ANIMATION_PERIOD = 6;

    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;

    public MinerNotFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
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

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.getResourceCount() >= this.getResourceLimit())
        {
            Entity miner = MinerFull.createMinerFull(this.getId(), this.getResourceLimit(),
                    this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
                    this.getImages());

            world.removeEntity( this);
            scheduler.unscheduleAllEvents( this);

            world.addEntity( miner);
            scheduleActions( scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            this.setResourceCount(this.getResourceCount() + 1);
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = this.nextPositionMiner(world, target.getPosition());

            if (!this.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Action.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, Action.createAnimationAction(0), getAnimationPeriod());
    }

    //    Activity Interface Implementation

    @Override
    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler);
    }

    @Override
    public void executeActivityAction(EventScheduler scheduler) {
        executeMinerNotFullActivity(this.world, this.imageStore, scheduler);
    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore)
    {
        return new Action( this, world, imageStore, 0);
    }

    public void executeMinerNotFullActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(entity.getPosition(), Ore.class);

        if (!notFullTarget.isPresent() ||
                !moveToNotFull(world, notFullTarget.get(), scheduler) ||
                !transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent( entity,
                    Action.createActivityAction(entity, world, imageStore),
                    entity.getActionPeriod());
        }
    }
}