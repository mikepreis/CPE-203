package comparator;
import java.util.Comparator;

public class YearComparator implements Comparator<Song> {

    @Override
    public int compare(Song s1, Song s2) {
        if ( s1.getYear() < s2.getYear() ) {
            return -1;
        } else if ( s1.getYear() > s2.getYear() ) {
            return 1;
        } else {
            return 0;
        }
    }
}
