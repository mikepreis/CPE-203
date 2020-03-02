import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Process {

    public static List<Point> read(String filename) {

            BufferedReader br = null;
            FileReader fr = null;
            List<Point> pointArray = new ArrayList<Point>();

            try {

                fr = new FileReader(filename);
                br = new BufferedReader(fr);
                String line = br.readLine();

                while ( (line) != null) {

                    System.out.println(line);
                    String[] rawData = line.split(",");

                    for (int i = 0; i < rawData.length; i++) {

                        rawData[i] = rawData[i].trim();

                    }
                    System.out.println(rawData[0] + " " + rawData[1] + " " + rawData[2]);

                    Double x = Double.parseDouble(rawData[0]);
                    Double y = Double.parseDouble(rawData[1]);
                    Double z = Double.parseDouble(rawData[2]);

                    Point point = new Point(x, y, z);

                    pointArray.add(point);

                    line = br.readLine();
                }

            }
            catch (Exception x) {
                x.printStackTrace();
            }
            finally {
                if (fr != null) {
                    try {br.close();} catch (Exception ignoreMe) {}
                    try {fr.close();} catch (Exception ignoreMe) {}
                }
            }

            return pointArray;
        }

    public static List<Point> process() {
        List<Point> points = Process.read("positions.txt");
        Stream<Point> streamOfPoints = points.stream().filter(point -> point.getZ() <= 2.0);
        streamOfPoints = streamOfPoints.map(point -> point.scale(0.5));
        streamOfPoints = streamOfPoints.map(point -> point.translate(-150, -37));
        List<Point> result = streamOfPoints.collect(Collectors.toList());
        return result;
    }

    public static void write(String filename, List<Point> array) {
        try {
            FileWriter fr = new FileWriter(filename);
            BufferedWriter br = new BufferedWriter(fr);
            for ( Point p : array ) {

                br.write(p.getX() + ", " + p.getY() + ", " + p.getZ() +"\n");
            }
        } catch (Exception x) {
            x.printStackTrace();
        }

    }
}
