public interface ActivityInterface extends ActionInterface {
    void executeActivityAction(EventScheduler scheduler);
    void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore);
}
