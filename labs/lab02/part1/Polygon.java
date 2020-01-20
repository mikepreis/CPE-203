import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private final List<Point> points;

    public Polygon( List<Point> p) {
        points = p;
    }

    public List<Point> getPoints() {
        return points;
    }

//    public double perimeter() {
//
//        double x2;
//        double x1;
//        double y2;
//        double y1;
//
//        Point firstPoint = this.getPoints().get(0);
//
//        double sumOfLengths = 0.0;
//
//        for ( int i = 0; i < this.getPoints().size()-1; i++) {
//
//            x2 = this.getPoints().get(i+1).getX();
//            x1 = this.getPoints().get(i).getX();
//            y2 = this.getPoints().get(i+1).getY();
//            y1 = this.getPoints().get(i).getY();
//
//            sumOfLengths += Math.sqrt( ((x2 - x1) * ( x2 - x1 )) + ((y2 - y1) * ( y2 - y1 )));
//        }
//
//        x2 = this.getPoints().get(this.getPoints().size()-1).getX();
//        x1 = firstPoint.getX();
//        y2 = this.getPoints().get(this.getPoints().size()-1).getY();
//        y1 = firstPoint.getY();
//
//        sumOfLengths += Math.sqrt( ((x2 - x1) * ( x2 - x1 )) + ((y2 - y1) * ( y2 - y1 )));
//
//        return sumOfLengths;
//    }
}
