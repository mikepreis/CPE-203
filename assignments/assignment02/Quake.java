import processing.core.PImage;
import java.util.List;
import java.util.Random;

public class Quake implements EntityInterface, Activity, Animation {

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

    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final String QUAKE_ID = "quake";

    public Quake(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, WorldModel world, ImageStore imageStore, int repeatCount) {

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
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent( this, createActivityAction( world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent( this, createAnimationAction(QUAKE_ANIMATION_REPEAT_COUNT), getAnimationPeriod());
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler);
        executeAnimationAction(scheduler);
    }

    @Override
    public void executeActivityAction(EventScheduler scheduler) {
        executeQuakeActivity(this.world, this.imageStore, scheduler);
    }

    @Override
    public void executeAnimationAction(EventScheduler scheduler) {
        this.nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent( this, createAnimationAction(Math.max(this.repeatCount - 1, 0)), this.getAnimationPeriod());
        }
    }

    public Action createAnimationAction(int repeatCount)
    {
        return new Action(ActionKind.ANIMATION, this, null, null, repeatCount);
    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore)
    {
        return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
    }

    public void executeQuakeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents( this);
        world.removeEntity( this);
    }

    public static Entity createQuake(Point position, List<PImage> images)
    {
        return new Entity(QUAKE_ID, position, images, 0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }
}
