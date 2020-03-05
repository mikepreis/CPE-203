public class CircleTest
{
    public static void main(String[] args) {
        try
        {
            Circle c1 = new Circle(-1);
            System.out.println(c1);
        }
        catch (ZeroRadiusException | NegativeRadiusException e)
        {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("In finally.");
        }
        System.out.println("Done.");
    }
}