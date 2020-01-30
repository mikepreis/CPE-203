import processing.core.PImage;
import java.util.*;

final class WorldModel
{
   private int numRows;
   private int numCols;
   private Background background[][];
   private EntityInterface occupancy[][];
   private Set<EntityInterface> entities;
   private static final int ORE_REACH = 1;

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new EntityInterface[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public Set<EntityInterface> getEntities() {
      return this.entities;
   }

   public Optional<EntityInterface> findNearest(Point pos, Object kind)
   {
      List<EntityInterface> ofType = new LinkedList<>();
      for (EntityInterface entity : this.entities)
      {
         if (entity.getClass() == kind.getClass())
         {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pos);
   }

   public static Optional<EntityInterface> nearestEntity(List<EntityInterface> entities, Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         EntityInterface nearest = entities.get(0);
         int nearestDistance = nearest.getPosition().distanceSquared(pos);

         for (EntityInterface other : entities)
         {
            int otherDistance = other.getPosition().distanceSquared(pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   public void removeEntity(EntityInterface entity)
   {
      removeEntityAt(entity.getPosition());
   }

   public void removeEntityAt(Point pos)
   {
      if (withinBounds(pos) && getOccupancyCell(pos) != null)
      {
         EntityInterface entity = getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         this.entities.remove(entity);
         setOccupancyCell(pos, null);
      }
   }

   public Optional<EntityInterface> getOccupant(Point pos)
   {
      if (isOccupied(pos))
      {
         return Optional.of(getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   public EntityInterface getOccupancyCell(Point pos)
   {
      return this.occupancy[pos.getY()][pos.getX()];
   }

   public void setOccupancyCell(Point pos, EntityInterface entity)
   {
      this.occupancy[pos.getY()][pos.getX()] = entity;
   }

   public Background getBackgroundCell(Point pos)
   {
      return this.background[pos.getY()][pos.getX()];
   }

   public boolean withinBounds(Point pos)
   {
      return pos.getY() >= 0 && pos.getY() < this.numRows &&
              pos.getX() >= 0 && pos.getX() < this.numCols;
   }

   public boolean isOccupied(Point pos)
   {
      return withinBounds(pos) &&
              getOccupancyCell(pos) != null;
   }

   /*
   Assumes that there is no entity currently occupying the
   intended destination cell.
*/
   public void addEntity(EntityInterface entity)
   {
      if (withinBounds(entity.getPosition()))
      {
         setOccupancyCell(entity.getPosition(), entity);
         this.entities.add(entity);
      }
   }

   public void moveEntity(EntityInterface entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (withinBounds(pos) && !pos.equals(oldPos))
      {
         setOccupancyCell(oldPos, null);
         removeEntityAt(pos);
         setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   public Optional<Point> findOpenAround( Point pos)
   {
      for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++)
      {
         for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++)
         {
            Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
            if (withinBounds( newPt) &&
                    !isOccupied( newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }

   public void tryAddEntity( EntityInterface entity)
   {
      if (this.isOccupied( entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      this.addEntity( entity);
   }

   public Optional<PImage> getBackgroundImage( Point pos)
   {
      if (this.withinBounds(pos))
      {
         return Optional.of(getCurrentImage(getBackgroundCell(pos)));
      }
      else
      {
         return Optional.empty();
      }
   }

   public void setBackground( Point pos, Background background)
   {
      if (this.withinBounds( pos))
      {
         this.setBackgroundCell( pos, background);
      }
   }

   public void setBackgroundCell( Point pos, Background background)
   {
      this.background[pos.getY()][pos.getX()] = background;
   }

   public static PImage getCurrentImage(Object entity)
   {
      if (entity instanceof Background)
      {
         return ((Background)entity).getImages()
                 .get(((Background)entity).getImageIndex());
      }
      else if (entity instanceof EntityInterface)
      {
         return ((EntityInterface)entity).getImages().get(((EntityInterface)entity).getImageIndex());
      }
      else
      {
         throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", entity));
      }
   }

   public int getnumRows() {
      return this.numRows;
   }

   public int getnumCols() {
      return this.numCols;
   }
}
