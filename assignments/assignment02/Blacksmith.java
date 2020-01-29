import java.util.List;
import java.util.Map;
import java.util.Random;
import processing.core.PImage;

final class Blacksmith extends Entity
{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private static final Random rand = new Random();
    private static final int PROPERTY_KEY = 0;
    private static final String SMITH_KEY = "blacksmith";
    private static final int SMITH_NUM_PROPERTIES = 4;
    private static final int SMITH_ID = 1;
    private static final int SMITH_COL = 2;
    private static final int SMITH_ROW = 3;

    public Blacksmith(String id, Point position, List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    public List<PImage> getImages(){
        return this.images;
    }
    public Point getPosition(){
        return this.position;
    }
    public void setPosition(Point p){
        this.position = p;
    }
    public int getImageIndex(){
        return this.imageIndex;
    }
    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public static boolean parseSmith(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == SMITH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                    Integer.parseInt(properties[SMITH_ROW]));
            Entity entity = new Blacksmith(properties[SMITH_ID], pt, imageStore.getImageList( SMITH_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }

    public static boolean processLine(String line, WorldModel world, ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
            switch (properties[PROPERTY_KEY])
            {
                case SMITH_KEY:
                    return parseSmith(properties, world, imageStore);
            }
        }
        return false;
    }
}
