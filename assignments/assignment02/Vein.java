import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Vein implements EntityInterface {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    public Vein(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public List<PImage> getImages() {
        return images;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public int getActionPeriod() {
        return actionPeriod;
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, ActivityClass.createActivityAction(this, world, imageStore), this.actionPeriod);
    }

    public static Vein createVein(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Vein(id, position, images, 0, 0, actionPeriod, 0);
    }

    public void executeVeinActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent())
        {
            Ore ore = Ore.createOre(VirtualWorld.ORE_ID_PREFIX + this.id, openPt.get(), VirtualWorld.ORE_CORRUPT_MIN + VirtualWorld.rand.nextInt(VirtualWorld.ORE_CORRUPT_MAX - VirtualWorld.ORE_CORRUPT_MIN), imageStore.getImageList( VirtualWorld.ORE_KEY));
            world.addEntity(ore);
            scheduleActions( scheduler, world, imageStore);
        }

        scheduler.scheduleEvent( this, ActivityClass.createActivityAction(this, world, imageStore), this.actionPeriod);
    }

}