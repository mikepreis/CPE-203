final class Event
{
   private ActionInterface action;
   private long time;
   private EntityInterface entity;

   public Event(ActionInterface action, long time, EntityInterface entity)
   {
      this.action = action;
      this.time = time;
      this.entity = entity;
   }

   public long getTime() {
      return this.time;
   }

   public ActionInterface getAction() {
      return this.action;
   }

   public EntityInterface getEntity() {
      return this.entity;
   }
}
