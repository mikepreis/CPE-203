package comparator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.time.Year;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };
   @Test
   public void testArtistComparator()
   {
      ArtistComparator artistComparator = new ArtistComparator();

      assertEquals(true, artistComparator.compare(songs[5], songs[6]) < 0);
      assertEquals(false,  artistComparator.compare(songs[5], songs[6]) > 0);
      assertEquals(true,  artistComparator.compare(songs[3], songs[2]) > 0);
   }

   @Test
   public void testLambdaTitleComparator()
   {
      Comparator<Song> titleComparator = (Song s1, Song s2) -> (s1.getTitle().compareTo(s2.getTitle()));

      assertEquals(true, titleComparator.compare(songs[5], songs[6]) < 0);
      assertEquals(true, titleComparator.compare(songs[6], songs[4]) < 0);
      assertEquals(true, titleComparator.compare(songs[1], songs[2]) < 0);
   }




   @Test
   public void testYearExtractorComparator()
   {
      Comparator<Song> yearComparator = Comparator.comparing(Song::getYear, (s1, s2) -> s2.compareTo(s1));
      System.out.println(yearComparator.compare(songs[0], songs[2]));

   }

   @Test
   public void testComposedComparator()
   {
      Comparator<Song> ac = Comparator.comparing(Song::getArtist);
      Comparator<Song> yc = Comparator.comparing(Song::getYear);
      ComposedComparator cc = new ComposedComparator(ac, yc);

      assertEquals(true, cc.compare(songs[5], songs[7]) < 0);
      assertEquals(false, cc.compare(songs[5], songs[7]) > 0);
      assertEquals(true, cc.compare(songs[2], songs[5]) < 0);



   }

   @Test
   public void testThenComparing()
   {
      Comparator<Song> cc = Comparator.comparing(Song::getArtist).thenComparing(Song::getYear);

      assertEquals(true, cc.compare(songs[5], songs[7]) < 0);
   }

   @Test
   public void runSort()
   {
      Comparator<Song> comparator = Comparator.comparing(Song::getArtist).thenComparing(Song::getTitle).thenComparing(Song::getYear);


      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );

      songList.sort(
          comparator
      );

      assertEquals(songList, expectedList);
   }
}
