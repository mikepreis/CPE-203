import processing.core.PImage;
import java.util.List;
import java.util.Random;

public class Ore implements EntityInterface, Activity {

    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;
    public static final Random rand = new Random();

    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public static final String ORE_KEY = "ore";
    public static final int ORE_NUM_PROPERTIES = 5;
    public static final int ORE_ID = 1;
    public static final int ORE_COL = 2;
    public static final int ORE_ROW = 3;
    public static final int ORE_ACTION_PERIOD = 4;
    public static final String ORE_ID_PREFIX = "ore -- ";
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;

    public Ore(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, WorldModel world, ImageStore imageStore, int repeatCount) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public List<PImage> getImages() {
        return images;
    }

    @Override
    public int getImageIndex() {
        return imageIndex;
    }

    @Override
    public int getResourceLimit() {
        return resourceLimit;
    }

    @Override
    public int getResourceCount() {
        return resourceCount;
    }

    @Override
    public int getActionPeriod() {
        return actionPeriod;
    }

    @Override
    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, createActivityAction( world, imageStore), this.getActionPeriod());
    }

    public static Entity createOre(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Entity(id, position, images, 0, 0, actionPeriod, 0);
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler);
    }

    @Override
    public void executeActivityAction(EventScheduler scheduler) {
        executeOreActivity(this.world, this.imageStore, scheduler);
    }

    public void executeOreActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Entity blob = Ore_Blob.createOreBlob(this.getId() + Ore_Blob.BLOB_ID_SUFFIX,
                pos, this.getActionPeriod() / Ore_Blob.BLOB_PERIOD_SCALE,
                Ore_Blob.BLOB_ANIMATION_MIN +
                        rand.nextInt(Ore_Blob.BLOB_ANIMATION_MAX - Ore_Blob.BLOB_ANIMATION_MIN),
                imageStore.getImageList( Ore_Blob.BLOB_KEY));

        world.addEntity(blob);
        scheduleActions(scheduler, world, imageStore);
    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore)
    {
        return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
    }

}