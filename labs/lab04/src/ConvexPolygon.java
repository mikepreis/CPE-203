import java.awt.*;

public class ConvexPolygon implements Shape {

    private Point[] vertices = new Point [5];
    private Color color;

    public Point getVertex( int index ) {
        return vertices[index];
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getArea() {
        return 0;
    }

    public double getPerimeter() {
        return 0;
    }

    public void translate(Point p) {

    }
}
