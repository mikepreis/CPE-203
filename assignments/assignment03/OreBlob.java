import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class OreBlob extends Entity implements AnimationInterface {

    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;

    public OreBlob( String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public Point nextPositionOreBlob(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz,
                this.getPosition().getY());

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get().getClass() == Ore.class)))
        {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.get().getClass() == Ore.class)))
            {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }

    public boolean moveToOreBlob(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPositionOreBlob(world, target.getPosition());

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

//    Activity Interface Implementation

    @Override
    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler);
    }

    @Override
    public void executeActivityAction(EventScheduler scheduler) {
        executeOreBlobActivity(this.world, this.imageStore, scheduler);
    }



    public Action createActivityAction(WorldModel world, ImageStore imageStore)
    {
        return new Action( this, world, imageStore, 0);
    }



    public static OreBlob createOreBlob(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new OreBlob(id, position, images, 0, 0, actionPeriod, animationPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, this.createAnimationAction(0), getAnimationPeriod());
    }

    public void executeOreBlobActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blobTarget = world.findNearest(this.getPosition(), Vein.class);
        long nextPeriod = this.getActionPeriod();

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().getPosition();

            if (moveToOreBlob(world, blobTarget.get(), scheduler))
            {
                Entity quake = Quake.createQuake(tgtPos,
                        imageStore.getImageList(Entity.QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.getActionPeriod();
                scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), nextPeriod);
    }

    //    Animation Interface Implementation

    public void executeAnimationAction(EventScheduler scheduler)
    {
        this.nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent( this, this.createAnimationAction(Math.max(this.repeatCount - 1, 0)), this.getAnimationPeriod());
        }
    }

    public Action createAnimationAction(int repeatCount)
    {
        return new Action( this, null, null, repeatCount);
    }
}
