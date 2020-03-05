public class NegativeRadiusException extends CircleException {

    private double radius = 0.0;

    public NegativeRadiusException(double radius) {
        super("negative radius");
        this.radius = radius;
    }

    public double radius() {
        return this.radius;
    }
}
