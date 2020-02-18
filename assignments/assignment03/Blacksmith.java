import processing.core.PImage;
import java.util.List;

public class Blacksmith extends Entity {


    private static final int SMITH_NUM_PROPERTIES = 4;
    private static final int SMITH_ID = 1;
    private static final int SMITH_COL = 2;
    private static final int SMITH_ROW = 3;

    public Blacksmith( String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

    }

    public static boolean parseSmith(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == SMITH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                    Integer.parseInt(properties[SMITH_ROW]));
            Entity entity = createBlacksmith(properties[SMITH_ID],
                    pt, imageStore.getImageList( SMITH_KEY));
            world.tryAddEntity( entity);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }

    public static Blacksmith createBlacksmith(String id, Point position, List<PImage> images)
    {
        return new Blacksmith(id, position, images,
                0, 0, 0, 0);
    }
}
