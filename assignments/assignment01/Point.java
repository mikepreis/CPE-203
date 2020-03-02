final class Point
{
   private final int x;
   private final int y;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other)
   {
      return other instanceof Point &&
         ((Point)other).x == this.x &&
         ((Point)other).y == this.y;
   }

   public boolean adjacent(Point p2)
   {
      return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) ||
              (this.y == p2.y && Math.abs(this.x - p2.x) == 1);
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }
}
