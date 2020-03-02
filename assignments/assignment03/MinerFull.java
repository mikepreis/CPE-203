import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class MinerFull extends Entity implements ActivityInterface, AnimationInterface  {

    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;
    private int animationPeriod;
    private int actionPeriod;

    public MinerFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod ) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public int getActionPeriod() {
        return actionPeriod;
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

    public void transformFull(MinerNotFull entity, WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        MinerNotFull miner = new MinerNotFull(entity.getId(), entity.getPosition(), entity.getImages(), entity.getResourceLimit(), 0, entity.getActionPeriod(), entity.getAnimationPeriod());

        world.removeEntity(entity);
        scheduler.unscheduleAllEvents(entity);

        world.addEntity(miner);
        scheduleActions(miner, scheduler, world, imageStore);
    }


    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition()))
        {
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

    public void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(entity, Action.createActivityAction( this, world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(entity, Action.createAnimationAction( this, 0), this.getAnimationPeriod());
    }

    //    Activity Interface Implementation

    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler);
        executeAnimationAction(scheduler);
    }

    public void executeActivityAction(EventScheduler scheduler) {
        executeMinerFullActivity(this, this.world, this.imageStore, scheduler);
    }

    public void executeMinerFullActivity(MinerNotFull entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest( entity.getPosition(), Blacksmith.class);

        if (fullTarget.isPresent() &&
                this.moveToFull( world, fullTarget.get(), scheduler))
        {
            transformFull(entity, world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent( entity, Action.createActivityAction(entity, world, imageStore), entity.getActionPeriod());
        }
    }


    public void executeAnimationAction(EventScheduler scheduler)
    {
        nextImage();

        if (repeatCount != 1)
        {
            scheduler.scheduleEvent(this, Action.createAnimationAction(this, Math.max(repeatCount - 1, 0)), this.getAnimationPeriod());
        }
    }
}
