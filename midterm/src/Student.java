import java.util.ArrayList;
import java.util.List;

public class Student {

    public String studentName;
    public List<String> courses = new ArrayList<>();

    public Student(String name) {
        studentName = name;
    }

    public void addClass(String classname) {
        courses.add(classname);
    }
}
