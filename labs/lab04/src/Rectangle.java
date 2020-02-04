import java.awt.*;

public class Rectangle implements Shape{

    private double height;
    private double width;
    private Point topLeftCorner;
    private Color color;

    public Rectangle(int h, int w, Point tlc, Color c) {
        height = h;
        width = w;
        topLeftCorner = tlc;
        color = c;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Point getTopLeftCorner() {
        return this.topLeftCorner;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getArea() {
        return this.width * this.height;
    }

    public double getPerimeter() {
        return 2 * (this.height + this.width);
    }

    public void translate(Point p) {
        this.topLeftCorner = p;
    }
}
