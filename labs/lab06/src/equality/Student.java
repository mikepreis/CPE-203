package equality;

import java.util.List;
import java.util.Objects;

class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
      final List<CourseSection> currentCourses)
   {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }


   public int hashCode(Objects o) {
      return o.hashCode();
   }

   @Override
   public boolean equals(Object o) {
      if ( o == null ) {
         return false;
      }
      if ( ! ( o instanceof Student ) ) {
         return false;
      }

      Student other = ( Student ) o;

      if ( ! (this.surname.equals(other.surname) ) ) {
         return false;
      }
      if ( ! (this.givenName.equals(other.givenName) ) ) {
         return false;
      }
      if ( ! (this.age == other.age ) ) {
         return false;
      }
      if ( ! (this.currentCourses.equals(other.currentCourses) ) ) {
         return false;
      }

      return true;
   }

}
