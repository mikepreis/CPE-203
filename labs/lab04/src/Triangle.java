import java.awt.Point;
import java.awt.Color;

public class Triangle implements Shape {

    private Point vertexA;
    private Point vertexB ;
    private Point vertexC;
    private Color color;

    public Triangle(Point a, Point b, Point c, Color color) {
        this.vertexA = a;
        this.vertexB = b;
        this.vertexC = c;
        this.color = color;
    }

    public Point getVertexA() {
        return vertexA;
    }

    public Point getVertexB() {
        return vertexB;
    }

    public Point getVertexC() {
        return vertexC;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getArea() {
        double ab = Math.sqrt( ( ( vertexB.getX() - vertexA.getX() ) *  (vertexB.getX() - vertexA.getX() ) ) +  ( ( vertexB.getY() - vertexA.getY() ) * ( vertexB.getY() - vertexA.getY() )) );
        double bc = Math.sqrt( ( ( vertexC.getX() - vertexB.getX() ) * ( vertexC.getX() - vertexB.getX() ) ) +  ( ( vertexC.getY() - vertexB.getY() ) * ( vertexC.getY() - vertexB.getY() )) );
        return 0.5 * ab * bc;
    }

    public double getPerimeter() {
        double ab = Math.sqrt( ( ( vertexB.getX() - vertexA.getX() ) *  (vertexB.getX() - vertexA.getX() ) ) +  ( ( vertexB.getY() - vertexA.getY() ) * ( vertexB.getY() - vertexA.getY() )) );
        double bc = Math.sqrt( ( ( vertexC.getX() - vertexB.getX() ) * ( vertexC.getX() - vertexB.getX() ) ) +  ( ( vertexC.getY() - vertexB.getY() ) * ( vertexC.getY() - vertexB.getY() )) );
        double ca = Math.sqrt( ( ( vertexA.getX() - vertexC.getX() ) * ( vertexA.getX() - vertexC.getX() ) ) +  ( ( vertexA.getY() - vertexC.getY() ) * ( vertexA.getY() - vertexC.getY() )) );
        return ab + bc + ca;
    }

    public void translate(Point p) {
        this.vertexA.x = this.vertexA.x + p.x;
        this.vertexA.y = this.vertexA.y + p.y;
        this.vertexB.x = this.vertexB.x + p.x;
        this.vertexB.y = this.vertexB.y + p.y;
        this.vertexC.x = this.vertexC.x + p.x;
        this.vertexC.y = this.vertexC.y + p.y;
    }
}
