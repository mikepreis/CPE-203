import java.awt.*;

public interface Shape {
    Color getColor();
    void setColor(Color c);
    double getArea();
    double getPerimeter();
    void translate(Point p);
}
