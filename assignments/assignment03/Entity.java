import java.util.List;
import java.util.Random;
import processing.core.PImage;
public abstract class Entity
{
   private String id;
   private Point position;
   private List<PImage> images;
   private int imageIndex;
   private int resourceLimit;
   private int resourceCount;
   public int repeatCount;
   private int animationPeriod;
   private int actionPeriod;

   private static final Random rand = new Random();
   private static final String QUAKE_KEY = "quake";
   private static final String QUAKE_ID = "quake";
   private static final String BLOB_KEY = "blob";

   public Entity(String id, Point position,
      List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
   {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
      this.animationPeriod = animationPeriod;
      this.actionPeriod = actionPeriod;
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
   public String getId() {
      return id;
   }
   public void setResourceCount(int resourceCount) {
      this.resourceCount = resourceCount;
   }
   public int getResourceCount() {
      return resourceCount;
   }
   public int getResourceLimit() {
      return resourceLimit;
   }
   public void nextImage()
   {
      this.imageIndex = (this.imageIndex + 1) % this.images.size();
   }

   public int getActionPeriod() {
      return actionPeriod;
   }

   public int getAnimationPeriod() {
      return animationPeriod;
   }
}
