import java.awt.*;

public class ConvexPolygon implements Shape {

    private Point[] vertices = new Point [5];
    private Color color;

    public Point getVertex( int index ) {
        return vertices[index];
    }

    


}
