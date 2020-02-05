import java.awt.*;

public class ConvexPolygon implements Shape {

    private Point[] vertices = new Point [5];
    private Color color;

    public ConvexPolygon(Point[] v, Color c) {
        this.vertices = v;
        this.color = c;
    }

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
        double sumPoints = 0.0;
        for ( int i = 0; i < vertices.length -1; i++ ) {
            sumPoints += ( (vertices[i].x * vertices[i+1].y) - (vertices[i].y * vertices[i+1].x) );
        }
        sumPoints += (vertices[vertices.length-1].x * vertices[0].y) - (vertices[vertices.length-1].y * vertices[0].x);
        return 0.5 * sumPoints;
    }

    public double getPerimeter() {

        double x2;
        double x1;
        double y2;
        double y1;

        double sumOfLengths = 0.0;

        for ( int i = 0; i < this.vertices.length-1; i++) {

            x2 = this.vertices[i+1].x;
            x1 = this.vertices[i].x;
            y2 = this.vertices[i+1].y;
            y1 = this.vertices[i].y;

            sumOfLengths += Math.sqrt( ((x2 - x1) * ( x2 - x1 )) + ((y2 - y1) * ( y2 - y1 )));
        }

        x2 = this.vertices[-1].x;
        x1 = this.vertices[0].x;
        y2 = this.vertices[-1].y;
        y1 = this.vertices[0].y;

        sumOfLengths += Math.sqrt( ((x2 - x1) * ( x2 - x1 )) + ((y2 - y1) * ( y2 - y1 )));

        return sumOfLengths;
    }

    public void translate(Point p) {
        for (int i = 0; i < vertices.length; i++) {
            vertices[i].x = vertices[i].x + p.x;
            vertices[i].y = vertices[i].y + p.y;
        }
    }
}
