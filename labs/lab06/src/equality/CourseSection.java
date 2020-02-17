package equality;

import java.time.LocalTime;

class CourseSection
{
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
      final int enrollment, final LocalTime startTime, final LocalTime endTime)
   {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   public boolean equals( Object o ) {
      if ( o == null ) {
         return false;
      }
      if ( ! ( o instanceof CourseSection ) ) {
         return false;
      }

      CourseSection otherCourseSection = ( CourseSection ) o;

      if ( ! (this.prefix.equals(otherCourseSection.prefix) ) ) {
         return false;
      }
      if ( ! (this.number.equals(otherCourseSection.number) ) ) {
         return false;
      }
      if ( ! (this.enrollment == otherCourseSection.enrollment ) ) {
         return false;
      }
      if ( ! (this.startTime.equals(otherCourseSection.startTime) ) ) {
         return false;
      }
      if ( ! (this.endTime.equals(otherCourseSection.endTime) ) ) {
         return false;
      }
      return true;

   }

   public int hashCode() {
      return (int) prefix.hashCode() + number.hashCode() + enrollment + startTime.hashCode() + endTime.hashCode();
   }

   // additional likely methods not defined since they are not needed for testing
}
