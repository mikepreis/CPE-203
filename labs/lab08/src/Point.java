public class Point {

    private double x;
    private double y;
    private double z;

    public Point(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getZ()
    {
        return this.z;
    }

    public Point scale(double n)
    {
        this.x = this.x * n;
        this.y = this.y * n;
        this.z = this.z * n;
        return new Point(this.getX(), this.getY(), this.getZ());
    }

    public Point translate(double x_, double y_)
    {
        this.x = x + x_;
        this.y = y + y_;
        return new Point(this.getX(), this.getY(), this.getZ());
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

        return new Point(xprime,yprime, this.z);
    }

}