import sun.jvm.hotspot.debugger.linux.sparc.LinuxSPARCThreadContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LogAnalyzer
{
      //constants to be used when pulling data out of input
      //leave these here and refer to them to pull out values
   private static final String START_TAG = "START";
   private static final int START_NUM_FIELDS = 3;
   private static final int START_SESSION_ID = 1;
   private static final int START_CUSTOMER_ID = 2;
   private static final String BUY_TAG = "BUY";
   private static final int BUY_NUM_FIELDS = 5;
   private static final int BUY_SESSION_ID = 1;
   private static final int BUY_PRODUCT_ID = 2;
   private static final int BUY_PRICE = 3;
   private static final int BUY_QUANTITY = 4;
   private static final String VIEW_TAG = "VIEW";
   private static final int VIEW_NUM_FIELDS = 4;
   private static final int VIEW_SESSION_ID = 1;
   private static final int VIEW_PRODUCT_ID = 2;
   private static final int VIEW_PRICE = 3;
   private static final String END_TAG = "END";
   private static final int END_NUM_FIELDS = 2;
   private static final int END_SESSION_ID = 1;

      //a good example of what you will need to do next
      //creates a map of sessions to customer ids
      //each session has a list of customerIds
   private static void processStartEntry(final String[] words, final Map<String, List<String>> sessionsFromCustomer)
   {
      if (words.length != START_NUM_FIELDS)
      {
         return;
      }
         //check if there already is a list entry in the map
         //for this customer, if not create one
      List<String> sessions = sessionsFromCustomer.get(words[START_CUSTOMER_ID]);
      if (sessions == null)
      {
         sessions = new LinkedList<>();
         sessionsFromCustomer.put(words[START_CUSTOMER_ID], sessions);
      }

         //now that we know there is a list, add the current session
      sessions.add(words[START_SESSION_ID]);
   }

      //similar to processStartEntry, should store relevant view
      //data in a map - model on processStartEntry, but store
      //your data to represent a view in the map (not a list of strings)
   private static void processViewEntry(final String[] words, final Map<String, List<View>> productsFromSession)
   {
      if (words.length != VIEW_NUM_FIELDS)
      {
         return;
      }

      List<View> products = productsFromSession.get(words[VIEW_SESSION_ID]);

      if (products == null)
      {
         products = new LinkedList<>();
         productsFromSession.put(words[VIEW_SESSION_ID], products);
      }
      products.add(new View(new Product(words[VIEW_PRODUCT_ID], Integer.parseInt(words[VIEW_PRICE]))));
   }
      //similar to processStartEntry, should store relevant purchases
      //data in a map - model on processStartEntry, but store
      //your data to represent a purchase in the map (not a list of strings)
   private static void processBuyEntry(final String[] words, final Map<String, List<Buy>> productsFromSession)
   {
      if (words.length != BUY_NUM_FIELDS)
      {
         return;
      }
      List<Buy> products = productsFromSession.get(words[BUY_SESSION_ID]);

      if (products == null)
      {
         products = new LinkedList<>();
         productsFromSession.put(words[BUY_SESSION_ID], products);
      }

      products.add(new Buy(new Product(words[BUY_PRODUCT_ID], Integer.parseInt(words[BUY_PRICE]), Integer.parseInt(words[BUY_QUANTITY]))));
   }

   private static void processEndEntry(final String[] words, final Map<String, List<String>> endSessionsFromCustomer)
   {
      if (words.length != END_NUM_FIELDS)
      {
         return;
      }
      List<String> sessions = endSessionsFromCustomer.get(words[END_SESSION_ID]);
      if (sessions == null)
      {
         sessions = new LinkedList<>();
         endSessionsFromCustomer.put(words[END_SESSION_ID], sessions);
      }

      sessions.add(words[END_SESSION_ID]);

   }

   private static void processLine(final String line, final Map<String, List<String>> sessionsFromCustomer, Map<String, List<View>> viewProductsFromSession, Map<String, List<Buy>> buyProductsFromSession, Map<String, List<String>> endSessions)
   {
      final String[] words = line.split("\\h");

      if (words.length == 0)
      {
         return;
      }

      switch (words[0])
      {
         case START_TAG:
            processStartEntry(words, sessionsFromCustomer);
            break;
         case VIEW_TAG:
            processViewEntry(words, viewProductsFromSession);
            break;
         case BUY_TAG:
            processBuyEntry(words, buyProductsFromSession);
            break;
         case END_TAG:
            processEndEntry(words,endSessions);
            break;
      }
   }

//   private static void printSessionPriceDifference(Map<String, List<String>> sessionsFromCustomer, Map<String, List<Buy>> purchasedProductsFromSession)
//   {
//      System.out.println("Price Difference for Purchased Product by Session");
//
//      for(Map.Entry<String, List<String>> entry : sessionsFromCustomer.entrySet()) {
//         List<String> sessions = entry.getValue();
//         for (String sessionId : sessions) {
//            List<Buy> theBuys = purchasedProductsFromSession.get(sessionId);
//            int total = 0;
//            for (Buy buy : theBuys) {
//               Product p = buy.getProduct();
//            }
//         }
//      }
//
//   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printCustomerItemViewsForPurchase(
      /* add parameters as needed */
      )
   {
      System.out.println("Number of Views for Purchased Product by Customer");

      /* add printing */

   }

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printStatistics(Map<String, List<String>> sessionsFromCustomer, Map<String, List<View>> viewsFromSession, Map<String, List<Buy>> buysFromSession)
   {
      //printSessionPriceDifference( /*add arguments as needed */);
      //printCustomerItemViewsForPurchase( /*add arguments as needed */);

      /* This is commented out as it will not work until you read
         in your data to appropriate data structures, but is included
         to help guide your work - it is an example of printing the
         data once propogated

       */
         printOutExample(sessionsFromCustomer, viewsFromSession, buysFromSession);

   }

   /* provided as an example of a method that might traverse your
      collections of data once they are written 
      commented out as the classes do not exist yet - write them! */

   private static void printOutExample(
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession)
   {
      //for each customer, get their sessions
      //for each session compute views
      for(Map.Entry<String, List<String>> entry:
         sessionsFromCustomer.entrySet())
      {
         System.out.println(entry.getKey());
         List<String> sessions = entry.getValue();
         for(String sessionID : sessions)
         {
            System.out.println("\tin " + sessionID);
            List<View> theViews = viewsFromSession.get(sessionID);
            for (View thisView: theViews)
            {
               System.out.println("\t\tviewed " + thisView.getProduct());
            }
         }
      }

      for(Map.Entry<String, List<String>> entry:
              sessionsFromCustomer.entrySet())
      {
         System.out.println(entry.getKey());
         List<String> sessions = entry.getValue();
         for(String sessionID : sessions)
         {
            System.out.println("\tin " + sessionID);
            List<Buy> theBuys = buysFromSession.get(sessionID);
            for (Buy thisBuy: theBuys)
            {
               System.out.println("\t\tviewed " + thisBuy.getProduct());
            }
         }
      }

   }


   private static void processFile(
      final Scanner input,
      final Map<String, List<String>> sessionsFromCustomer, Map<String, List<View>> viewProductsFromSession, Map<String, List<Buy>> buyProductsFromSession, Map<String, List<String>> endSessions)
   {
      while (input.hasNextLine())
      {
         processLine(input.nextLine(), sessionsFromCustomer, viewProductsFromSession, buyProductsFromSession, endSessions);
      }
   }

   private static void populateDataStructures(final String filename, final Map<String, List<String>> sessionsFromCustomer, Map<String, List<View>> viewProductsFromSession, Map<String, List<Buy>> buyProductsFromSession, Map<String, List<String>> endSessions) throws FileNotFoundException
   {
      try (Scanner input = new Scanner(new File(filename)))
      {
         processFile(input, sessionsFromCustomer, viewProductsFromSession, buyProductsFromSession, endSessions);
      }
   }

   private static String getFilename(String[] args)
   {
      if (args.length < 1)
      {
         System.err.println("Log file not specified.");
         System.exit(1);
      }

      return args[0];
   }

   public static void main(String[] args)
   {
      final Map<String, List<String>> sessionsFromCustomer = new HashMap<>();
      final Map<String, List<View>> viewProductsFromSession = new HashMap<>();
      final Map<String, List<Buy>> buyProductsFromSession = new HashMap<>();
      final Map<String, List<String>> endSessions = new HashMap<>();

      final String filename = getFilename(args);

      try
      {
         populateDataStructures(filename, sessionsFromCustomer, viewProductsFromSession, buyProductsFromSession, endSessions);
         printStatistics(sessionsFromCustomer, viewProductsFromSession, buyProductsFromSession);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }
}
