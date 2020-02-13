public class Animation implements ActionInterface {

    private EntityInterface entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Animation(EntityInterface entity, WorldModel world, ImageStore imageStore, int repeatCount)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        executeAnimationAction(scheduler);
    }

    @Override
    public void executeAnimationAction(EventScheduler scheduler) {
        entity.nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent( this.entity, createAnimationAction(Math.max(this.repeatCount - 1, 0)), entity.getAnimationPeriod());
        }
    }

    public Animation createAnimationAction(int repeatCount)
    {
        return new Animation(this.entity, null, null, repeatCount);
    }



}
