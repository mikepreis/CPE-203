public class Bigger {

    public static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon) {

        double circlePerimeter = Util.perimeter(circle);
        double rectanglePerimeter = Util.perimeter(rectangle);
        double polygonPerimeter = Util.perimeter(polygon);
        double resultFromFirstComparison = Math.max(circlePerimeter, rectanglePerimeter);

        return Math.max(polygonPerimeter, resultFromFirstComparison);
    }

    public static double whichIsBigger2(Circle circle, Rectangle rectangle, Polygon polygon) {

        double circlePerimeter = circle.perimeter();
        double rectanglePerimeter = rectangle.perimeter();
        double polygonPerimeter = polygon.perimeter();
        double resultFromFirstComparison = Math.max(circlePerimeter, rectanglePerimeter);

        return Math.max(polygonPerimeter, resultFromFirstComparison);
    }
}


