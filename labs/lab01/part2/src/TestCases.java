import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;


public class TestCases
{
   public static final double DELTA = 0.00001;

   @Test
   public void testGetX()
   {
      assertEquals(1.0, new Point(1.0, 2.0).getX(), DELTA);
   }

   @Test
   public void testGetX2()
   {
      assertEquals(6.0, new Point(6.0, 2.0).getX(), DELTA);
   }

   @Test
   public void testGetY()
   {
      assertEquals(2.0, new Point(1.0, 2.0).getY(), DELTA);
   }

   @Test
   public void testGetY2()
   {
      assertEquals(5.0, new Point(1.0, 5.0).getY(), DELTA);
   }

   @Test
   public void testGeRadius()
   {
      assertEquals(2.8284271247461903, new Point(2.0, 2.0).getRadius(), DELTA);
   }

   @Test
   public void testGeRadius2() { assertEquals(8.602325267042627, new Point(5.0, 7.0).getRadius(), DELTA); }

   @Test
   public void testGetAngle()
   {
      assertEquals(0.7853981633974483, new Point(2.0, 2.0).getAngle(), DELTA);
   }

   @Test
   public void testGetAngle2()
   {
      assertEquals(0.3217505543966422, new Point(6.0, 2.0).getAngle(), DELTA);
   }

   @Test
   public void testRotate90() {
      Point newPoint = new Point(2.0, 2.0).rotate90();
      assertEquals(-2.0, newPoint.getX(), DELTA);
      assertEquals(2.0, newPoint.getY(), DELTA);
   }

   @Test
   public void testRotate902() {
        Point newPoint = new Point(3.0, 4.0).rotate90();
        assertEquals(-4.0, newPoint.getX(), DELTA);
        assertEquals(3.0, newPoint.getY(), DELTA);
   }

   /*
    * The tests below here are to verify the basic requirements regarding
    * the "design" of your class.  These are to remain unchanged.
    */

   @Test
   public void testImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getX",
         "getY",
         "getRadius",
         "getAngle",
         "rotate90"
         );

      final List<Class> expectedMethodReturns = Arrays.asList(
         double.class,
         double.class,
         double.class,
         double.class,
         Point.class
         );

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0],
         new Class[0],
         new Class[0],
         new Class[0],
         new Class[0]
         );

      verifyImplSpecifics(Point.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException
   {
      assertEquals("Unexpected number of public fields",
         0, Point.class.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertEquals("Unexpected number of public methods",
         expectedMethodNames.size(), publicMethods.size());

      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodReturns.size());
      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodParameters.size());

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }

      // verify that fields are final
      final List<Field> nonFinalFields = Arrays.stream(
         clazz.getDeclaredFields())
            .filter(f -> !Modifier.isFinal(f.getModifiers()))
            .collect(Collectors.toList());

      assertEquals("Unexpected non-final fields", 0, nonFinalFields.size());
   }
}
