import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Midterm {

    public static void main(String[] args) {
        //A filter removes undesired elements from a stream
        //A stream does not store it's data, data comes from elsewhere i.e. database
        Stream<String> wordsList  = Stream.of("Mike", "John", "chris", "kevin", "applesauce");
        List<String> wordList = new ArrayList<>();
        wordList.add("james");
        wordList.add("thomas");
        wordList.add("kat");
        wordList.add("michael");
        wordList.add("sarah");
        wordList.add("Nicole");
        wordList.add("Doug");
        Long wordListStream = wordList.stream().filter(w -> w.length() > 5).count();
        long count = wordsList.filter(word -> word.length() > 4 ).count();
        //System.out.println("Count of words with more than 4 characters : " + count);
        //System.out.println("Count of words with more than 5 characters : " + wordListStream);

        List<Student> listOfStudents = new ArrayList<>();
        Student mike = new Student("Mike");
        Student john = new Student("John");
        Student chris = new Student("Chris");
        Student kyle = new Student("Kyle");
        mike.addClass("CPE357");
        mike.addClass("CPE203");
        mike.addClass("CPE202");
        mike.addClass("ART200");

        john.addClass("ART457");
        john.addClass("CPE203");
        john.addClass("CPE202");
        john.addClass("ART200");

        chris.addClass("CPE357");
        chris.addClass("CPE203");
        chris.addClass("CPE202");
        chris.addClass("ART200");

        listOfStudents.add(mike);
        listOfStudents.add(john);
        listOfStudents.add(chris);


        //Write a Stream expression to return the sorted names of students who have CSC203 in their courses (use ‘contains’ to check for an element in a list)
        List<String> students = listOfStudents.stream().filter( s -> s.courses.contains("CPE357")).map(student -> student.studentName).sorted().collect(Collectors.toList());


    }
}
