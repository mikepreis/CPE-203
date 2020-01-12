import javax.swing.*;
import java.util.*;

class ExampleMap
{

   public static List<String> highEnrollmentStudents(Map<String, List<Course>> courseListsByStudentName, int unitThreshold)
   {
      List<String> overEnrolledStudents = new LinkedList<>();

      /*
         Build a list of the names of students currently enrolled
         in a number of units strictly greater than the unitThreshold.
      */

      for (String studentName : courseListsByStudentName.keySet()) {
         int totalUnits = 0;
         List<Course> courses = courseListsByStudentName.get(studentName);
         for ( Course c : courses) {
            totalUnits += c.getNumUnits();
         }
         if (totalUnits > unitThreshold) {
            overEnrolledStudents.add(studentName);
         }
      }

      for (String name : overEnrolledStudents) {
         System.out.println(name);
      }

      return overEnrolledStudents;      
   }

   public static void main(String[ ] args ) {

      Map<String, List<Course>> courseListsByStudent = new HashMap<>();

      courseListsByStudent.put("Julie",
              Arrays.asList(
                      new Course("CPE 123", 4),
                      new Course("CPE 101", 4),
                      new Course("CPE 202", 4),
                      new Course("CPE 203", 4),
                      new Course("CPE 225", 4)));
      courseListsByStudent.put("Paul",
              Arrays.asList(
                      new Course("CPE 101", 4),
                      new Course("CPE 202", 4),
                      new Course("CPE 203", 4),
                      new Course("CPE 225", 4)));
      courseListsByStudent.put("Zoe",
              Arrays.asList(
                      new Course("CPE 123", 4),
                      new Course("CPE 203", 4),
                      new Course("CPE 471", 4),
                      new Course("CPE 473", 4),
                      new Course("CPE 476", 4),
                      new Course("CPE 572", 4)));


      highEnrollmentStudents(courseListsByStudent, 16);

   }

}
