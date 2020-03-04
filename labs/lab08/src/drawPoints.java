import processing.core.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class drawPoints extends PApplet {

	public void settings() {
    size(500, 500);
	}
  
	public void setup() {
    	background(180);
    	noLoop();
  	}

  	public void draw() {

   	double x, y;
   
   	String[] lines = loadStrings("positions.txt");
   	println("there are " + lines.length);
	List<Point> points = Stream.of(lines).map(s -> s.split(", ")).map(s -> new Point(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]))).filter(point -> point.getZ() <= 2.0).map(point -> point.scale(0.5)).map(point -> point.translate(-150, -37)).collect(Collectors.toList());
	for (Point p : points) {
		ellipse((int) p.getX(), (int) p.getY(), 1, 1);
	}
  	}

  	public static void main(String args[]) {
      PApplet.main("drawPoints");
   }
}
