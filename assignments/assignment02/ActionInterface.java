public interface ActionInterface {
    void executeAction(EventScheduler scheduler);
    void executeAnimationAction(EventScheduler scheduler);
    Animation createAnimationAction(int repeatCount);
    void executeActivityAction(EventScheduler scheduler);
    void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    Activity createActivityAction(WorldModel world, ImageStore imageStore);
}
