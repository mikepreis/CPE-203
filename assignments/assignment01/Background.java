import java.util.List;
import processing.core.PImage;

final class Background
{
   private String id;
   private List<PImage> images;
   private int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }

   public String getId() {
      return id;
   }

   public static PImage getCurrentImage(Object entity)
   {
      if (entity instanceof Background)
      {
         return ((Background)entity).getImages().get(((Background)entity).imageIndex);
      }
      else if (entity instanceof Entity)
      {
         return ((Entity)entity).getImages().get(((Entity)entity).getImageIndex());
      }
      else
      {
         throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", entity));
      }
   }

   public List<PImage> getImages(){
      return this.images;
   }

}
