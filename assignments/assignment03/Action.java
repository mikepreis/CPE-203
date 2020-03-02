public class Action
{
   public Entity entity;
   public WorldModel world;
   public ImageStore imageStore;
   public int repeatCount;

   public Action(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount)
   {
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
      this.repeatCount = repeatCount;
   }

   public void executeAction(EventScheduler scheduler)
   {
   }

   public static void executeAnimationAction(Action action, EventScheduler scheduler)
   {
   }

   public static void executeActivityAction(Action action, EventScheduler scheduler)
   {

   }

   public static Action createAnimationAction(Entity entity, int repeatCount)
   {
      return new Action( entity, null, null, repeatCount);
   }

   public static Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore)
   {
      return new Action( entity, world, imageStore, 0);
   }

}
