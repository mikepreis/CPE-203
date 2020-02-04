import java.awt.*;

public class Circle implements Shape {

    private double radius;
    private Point center;
    private Color color;

    public Circle(double r,Color c, Point center) {
        this.radius = r;
        this.color = c;
        this.center = center;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double r) {
        this.radius = r;
    }

    public Point getCenter() {
        return center;
    }

    public double getPerimeter() {
        return 2 * Math.PI * this.getRadius();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color c ) {
        this.color = c;
    }

    public void translate(Point p) {
        this.setRadius( Math.sqrt( (( center.x - p.x ) *  ( center.x - p.x )) +  (( center.y - p.y ) *  ( center.y - p.y ))) );
    }

    public double getArea() {
        return Math.PI * getRadius() * getRadius();
    }




}
