import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Vein extends Activity implements EntityInterface {

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

    public Vein(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, WorldModel world, ImageStore imageStore, int repeatCount) {

        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;

        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
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

    @Override
    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler);
    }

    @Override
    public void executeActivityAction(EventScheduler scheduler) {
        executeVeinActivity(this.world, this.imageStore, scheduler);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, createActivityAction( world, imageStore), this.getActionPeriod());
    }

    public static Entity createVein(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Entity(id, position, images, 0, 0, actionPeriod, 0);
    }

    public void executeVeinActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.getPosition());

        if (openPt.isPresent())
        {
            Entity ore = Ore.createOre(Ore.ORE_ID_PREFIX + this.getId(),
                    openPt.get(), Ore.ORE_CORRUPT_MIN +
                            rand.nextInt(Ore.ORE_CORRUPT_MAX - Ore.ORE_CORRUPT_MIN),
                    imageStore.getImageList( Ore.ORE_KEY));
            world.addEntity( ore);
            scheduleActions( scheduler, world, imageStore);
        }

        scheduler.scheduleEvent( this, this.createActivityAction(world, imageStore), this.getActionPeriod());
    }

}