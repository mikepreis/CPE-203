public interface ActivityInterface extends ActionInterface{
    Action createActivityAction(WorldModel world, ImageStore imageStore);
    void executeActivityAction(EventScheduler scheduler);
}
