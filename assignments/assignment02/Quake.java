import processing.core.PImage;
import java.util.List;

public class Quake implements EntityInterface {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;


    public Quake(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent( this, ActivityClass.createActivityAction(this, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent( this, AnimationClass.createAnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT), getAnimationPeriod());
    }

    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
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
    public int getAnimationPeriod() { return animationPeriod; }
    public int getResourceCount() { return resourceCount; }
    public String getId() { return id; }
    public int getResourceLimit() { return resourceLimit; }

    public static Quake createQuake(Point position, List<PImage> images)
    {
        return new Quake(VirtualWorld.QUAKE_ID, position, images, 0, 0, VirtualWorld.QUAKE_ACTION_PERIOD, VirtualWorld.QUAKE_ANIMATION_PERIOD);
    }

    public void executeQuakeActivity(WorldModel world, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents( this);
        world.removeEntity( this);
    }
}
