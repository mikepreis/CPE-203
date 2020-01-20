public class Point {

    private final double x;
    private final double y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getRadius()
    {
        return Math.sqrt( (getX()*getX()) + (getY()*getY()) );
    }

    public double getAngle(){

        //returns in radians [-pi, pi]
        return (Math.atan2(y,x));

    }

    public Point rotate90(){

        double  xprime = this.getRadius()*Math.cos(this.getAngle()+Math.PI/2);
        double yprime =  this.getRadius()*Math.sin(this.getAngle()+Math.PI/2);

        return new Point(xprime,yprime);
    }

}
