public interface AnimationInterface extends ActivityInterface {
    void executeAnimationAction(EventScheduler scheduler);
    int getAnimationPeriod();

}
