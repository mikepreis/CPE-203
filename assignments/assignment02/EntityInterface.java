import processing.core.PImage;
import java.util.List;

public interface EntityInterface {
    void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    void nextImage();
    Point getPosition();
    void setPosition(Point p);
    int getAnimationPeriod();
    int getImageIndex();
    List<PImage> getImages();
}
