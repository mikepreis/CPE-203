import processing.core.PImage;
import java.util.List;

public class Ore implements EntityInterface {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    public Ore(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
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

    public void executeOreActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.position;    // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Ore_Blob blob = Ore_Blob.createOreBlob(this.id + VirtualWorld.BLOB_ID_SUFFIX,
                pos, this.actionPeriod / VirtualWorld.BLOB_PERIOD_SCALE,
                VirtualWorld.BLOB_ANIMATION_MIN +
                        VirtualWorld.rand.nextInt(VirtualWorld.BLOB_ANIMATION_MAX - VirtualWorld.BLOB_ANIMATION_MIN),
                imageStore.getImageList( VirtualWorld.BLOB_KEY));

        world.addEntity(blob);
        scheduleActions(scheduler, world, imageStore);
    }

    public static Ore createOre(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Ore(id, position, images, 0, 0, actionPeriod, 0);
    }

}