import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Workspace {

    private List<Shape> shapes = new ArrayList<>();

    public void add( Shape shape ) {
        shapes.add(shape);
    }

    public Shape get( int index ) {
        return shapes.get(index);
    }

    public int size() {
        return shapes.size();
    }

    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();
        for ( Shape shape : shapes ) {
            if (shape instanceof Circle) {
                circles.add(shape);
            }
        }
        return circles;
    }

    public List<Rectangle> getRectangles() {
        List<Rectangle> rectangles = new ArrayList<>();
        for ( Shape shape : shapes ) {
            if (shape instanceof Rectangle) {
                rectangles.add(shape);
            }
        }
        return rectangles;
    }

    public List<Triangle> getTriangles() {
        List<Triangle> triangles = new ArrayList<>();
        for ( Shape shape : shapes ) {
            if (shape instanceof Triangle) {
                triangles.add(shape);
            }
        }
        return triangles;
    }

    public List<ConvexPolygon> getConvexPolygon() {
        List<ConvexPolygon> convexPolygons = new ArrayList<>();
        for ( Shape shape : shapes ) {
            if (shape instanceof ConvexPolygon) {
                convexPolygons.add(shape);
            }
        }
        return convexPolygons;
    }

    public List<Shape> getShapesByColor( Color color ) {
        List<Shape> shapesByColor = new ArrayList<>();
        for ( Shape shape : shapes ) {
            if ( shape.getColor().equals(color) ) {
                shapesByColor.add(shape);
            }
        }
        return shapesByColor;
    }

    public double getAreaOfAllShapes() {
        double total = 0.0;
        for ( Shape shape : shapes ) {
            total += shape.getArea();
        }
        return total;
    }

    public double getParimeterOfAllShapes() {
        double total = 0.0;
        for ( Shape shape : shapes ) {
            total += shape.getPerimeter();
        }
        return total;
    }


}
