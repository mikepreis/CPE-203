import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

public class ExceptWhat
{
   private static void exceptional(boolean isBad) throws EOFException {
      if (isBad)
      {
         throw new EOFException();
      }
   }

   private static void outstanding() throws FileNotFoundException {
      throw new FileNotFoundException();
   }

   private static void fantastic()
   {
      throw new NullPointerException();
   }

   private static void intermediate3(boolean isBad) throws EOFException, FileNotFoundException, NullPointerException
   {
      // This method should not attempt to recover from any exceptions.
         exceptional(isBad);
         outstanding();
         fantastic();

   }

   private static void intermediate2(boolean isBad) throws FileNotFoundException, NullPointerException
   {
      /* This method should handle EOFExceptions and print
       * "intermediate 2 - eof handled" when so doing.  But it is not
       * capable of recovering from any other exceptions.
       */
      try {
         intermediate3(isBad);
      } catch ( EOFException e) {
         System.out.println("intermediate 2 - eof handled");
      }
   }

   private static void intermediate1()
   {
      /* This method should handle FileNotFoundExceptions and print
       * "intermediate 1 - handling missing file" when so doing.
       * But this method is not capable of recovering from any
       * other exceptions.
       */
      try {
         intermediate2(true);
      } catch (FileNotFoundException e) {
         System.out.println("intermediate 1 - handling missing file");
      }
   }

   private static void start1()
   {
      // Does not attempt to recover from any exceptions.
     intermediate1();
   }

   private static void start2() throws EOFException, FileNotFoundException
   {
      // Does not attempt to recover from any exceptions.
      intermediate2(false);
   }

   public static void main(String[] args)
   {
      /* Though there are exceptions to most rules, let's establish
       * for now that checked exceptions should not escape main.
       * As such, handle any checked exceptions raised below by
       * printing the "main handling " and the type of exception.
       */
      try {
         start1();
         start2();
      } catch (EOFException e) {
         System.out.println("main handling");
      } catch (FileNotFoundException e) {
         System.out.println("main handling file not found");
      }

   }
}
