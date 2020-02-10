package comparator;
import java.util.Comparator;

public class ComposedComparator implements Comparator<Song> {
     private Comparator<Song> comp1;
     private Comparator<Song> comp2;
    public ComposedComparator(Comparator<Song> c1, Comparator<Song> c2) {
        this.comp1 = c1;
        this.comp2 = c2;
    }
    public int compare(Song s1, Song s2) {

        if (comp1.compare(s1, s2) == 0) {
            return comp2.compare(s1, s2);
        }

        return comp1.compare(s1, s2);
    }
}
