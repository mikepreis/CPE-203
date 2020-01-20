public class Rectangle {

    private final Point topLeft;
    private final Point bottomRight;

    public Rectangle( Point tl, Point br ) {
        topLeft = tl;
        bottomRight = br;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

//    public double perimeter() {
//        return 2 + (
//                this.getBottomRight().getX() - this.getTopLeft().getX() +
//                        this.getTopLeft().getY() - this.getBottomRight().getY() );
//    }

}
