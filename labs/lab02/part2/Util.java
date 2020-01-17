public class Util {

    public static double perimeter( Circle c ) {
        return 2 * Math.PI * c.getRadius();
    }

    public static double perimeter( Rectangle r ) {
        return 2 + (
                r.getBottomRight().getX() - r.getTopLeft().getX() +
                        r.getTopLeft().getY() - r.getBottomRight().getY() );
    }

    public static double perimeter( Polygon p ) {

        double x2;
        double x1;
        double y2;
        double y1;

        Point firstPoint = p.getPoints().get(0);

        double sumOfLengths = 0.0;

        for ( int i = 0; i < p.getPoints().size()-1; i++) {

            x2 = p.getPoints().get(i+1).getX();
            x1 = p.getPoints().get(i).getX();
            y2 = p.getPoints().get(i+1).getY();
            y1 = p.getPoints().get(i).getY();

            sumOfLengths += Math.sqrt( ((x2 - x1) * ( x2 - x1 )) + ((y2 - y1) * ( y2 - y1 )));
        }

        x2 = p.getPoints().get(p.getPoints().size()-1).getX();
        x1 = firstPoint.getX();
        y2 = p.getPoints().get(p.getPoints().size()-1).getY();
        y1 = firstPoint.getY();

        sumOfLengths += Math.sqrt( ((x2 - x1) * ( x2 - x1 )) + ((y2 - y1) * ( y2 - y1 )));

        return sumOfLengths;
    }
}
