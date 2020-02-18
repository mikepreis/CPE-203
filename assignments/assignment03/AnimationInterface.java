public interface AnimationInterface extends ActionInterface {
    Action createAnimationAction(int repeatCount);
    void executeAnimationAction(EventScheduler scheduler);
}
