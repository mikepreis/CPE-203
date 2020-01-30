public interface ActionInterface {
    EntityInterface entity = null;
    WorldModel world = null;
    ImageStore imageStore = null;
    int repeatCount = 0;
    void executeAction(EventScheduler scheduler);
}
