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
   private int actionPeriod;
   private int animationPeriod;
   public static final Random rand = new Random();
   private static final int PROPERTY_KEY = 0;
   public static final String QUAKE_KEY = "quake";
   public static final String QUAKE_ID = "quake";
   public static final String BLOB_KEY = "blob";
   public static final String ORE_KEY = "ore";
   public static final String VEIN_KEY = "vein";
   public static final String SMITH_KEY = "blacksmith";
   public static final String OBSTACLE_KEY = "obstacle";
   public static final String MINER_KEY = "miner";
   public static final String BGND_KEY = "background";

   public Entity(String id, Point position,
      List<PImage> images, int resourceLimit, int resourceCount,
      int actionPeriod, int animationPeriod)
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
   public int getActionPeriod() {
      return actionPeriod;
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
   public int getAnimationPeriod()
   {
      if (MinerFull.class.equals(this.getClass()) || MinerNotFull.class.equals(this.getClass()) || OreBlob.class.equals(this.getClass()) || Quake.class.equals(this.getClass())) {
         return this.animationPeriod;
      }
      throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", this.getClass()));
   }

   public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);

   public static boolean processLine(String line, WorldModel world, ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
            case BGND_KEY:
               return Background.parseBackground(properties, world, imageStore);
            case MINER_KEY:
               return MinerNotFull.parseMiner(properties, world, imageStore);
            case OBSTACLE_KEY:
               return Obstacle.parseObstacle(properties, world, imageStore);
            case ORE_KEY:
               return Ore.parseOre(properties, world, imageStore);
            case SMITH_KEY:
               return Blacksmith.parseSmith(properties, world, imageStore);
            case VEIN_KEY:
               return Vein.parseVein(properties, world, imageStore);
         }
      }

      return false;
   }
}
