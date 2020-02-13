import processing.core.PImage;
import java.util.List;
public interface EntityInterface {
    String getId();
    Point getPosition();
    List<PImage> getImages();
    int getImageIndex();
    int getResourceLimit();
    int getResourceCount();
    int getActionPeriod();
    int getAnimationPeriod();
    void nextImage();
}
