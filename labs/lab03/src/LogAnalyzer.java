import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LogAnalyzer
{
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

   private static void processStartEntry(final String[] words, final Map<String, List<String>> sessionsFromCustomer)
   {
      if (words.length != START_NUM_FIELDS)
      {
         return;
      }

      List<String> sessions = sessionsFromCustomer.get(words[START_CUSTOMER_ID]);
      if (sessions == null)
      {
         sessions = new LinkedList<>();
         sessionsFromCustomer.put(words[START_CUSTOMER_ID], sessions);
      }

      sessions.add(words[START_SESSION_ID]);
   }

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
            processEndEntry(words, endSessions);
            break;
      }
   }
   private static void printAverageViewsWithoutPurchase(Map<String, List<String>> sessionsFromCustomer, Map<String, List<Buy>> productsFromSession, Map<String, List<View>> viewedFromSession) {

      int numSessions = 0;
      int numViews = 0;
      for ( Map.Entry<String, List<String>> entry : sessionsFromCustomer.entrySet() ) {

         for ( String session : entry.getValue() ) {

            if ( !(productsFromSession.containsKey(session)) ) {

               System.out.println(session);
               numSessions++;
               if (viewedFromSession.get(session) != null ) {
                  for ( View product : viewedFromSession.get(session) ) {

                     System.out.println(product.getProduct().getProductId());
                     numViews++;
                  }
               }
            }
         }
      }
      System.out.println("\nAverage Views without Purchase: " + (double) numViews / numSessions + "\n");
   }

   private static void printSessionPriceDifference(Map<String, List<String>> sessionsFromCustomer, Map<String, List<View>> viewsFromSession, Map<String, List<Buy>> buysFromSession)
   {
      System.out.println("Price Difference for Purchased Product by Session");

      for( Map.Entry<String, List<String>> entry : sessionsFromCustomer.entrySet() ){

         for ( String session :  entry.getValue() ) {

            if ( buysFromSession.containsKey(session) ) {

               System.out.println(session);

               int viewedTotal = 0;
               int viewedItems = 0;

               for (View product : viewsFromSession.get(session)) {
                  viewedItems++;
                  viewedTotal += product.getProduct().getPrice();
               }


               int viewedAverage = viewedTotal / viewedItems;

               for (Buy product : buysFromSession.get(session)) {
                  double difference = product.getProduct().getPrice() - viewedAverage;
                  System.out.println("\t" + product.getProduct().getProductId() + " " + difference);
               }

            }
         }
      }
   }
   private static void printCustomerItemViewsForPurchase(Map<String, List<String>> sessionFromCustomer, Map<String, List<View>> viewsFromSession, Map<String, List<Buy>> buysFromSession)
   {
      System.out.println("Number of Views for Purchased Product by Customer");

      for( Map.Entry<String, List<String>> entry : sessionFromCustomer.entrySet() ) {

         System.out.println(entry.getKey());
         //System.out.println(entry.getValue());

         for ( String session :  entry.getValue() ) {

            //System.out.println(session);

            if ( buysFromSession.containsKey(session) ) {

               for ( Buy product : buysFromSession.get(session) ) {

                  String p = product.getProduct().getProductId();
                  int numProductSessionViews = 0;

                  for ( String viewSession : entry.getValue() ) {

                     if ( viewsFromSession.get(viewSession) != null ) {
                        for ( View v : viewsFromSession.get(viewSession) ) {

                           if ( p.equals(v.getProduct().getProductId()) ) {

                              numProductSessionViews++;
                              break;
                           }
                        }
                     }

                     //System.out.println("Viewed Session : " + viewSession);
                  }
                  System.out.println("\t" + p + " " + numProductSessionViews);
               }
            }
         }
      }
   }
   private static void lookedAt(Map<String, List<String>> sessionsFromCustomer, Map<String, List<View>> viewsFromSession) {
      for (Map.Entry<String, List<String>> entry : sessionsFromCustomer.entrySet()) {
         System.out.println(entry.getKey());
         for (String session : entry.getValue()) {
            System.out.println("\tin " + session);
            if ( viewsFromSession.get(session) != null ) {
               for (View v : viewsFromSession.get(session)) {
                  System.out.println("\t\tlooked at " + v.getProduct().getProductId());
               }
            }
         }
      }
   }
   private static void printStatistics(Map<String, List<String>> sessionsFromCustomer, Map<String, List<View>> viewsFromSession, Map<String, List<Buy>> buysFromSession)
   {
      printAverageViewsWithoutPurchase(sessionsFromCustomer, buysFromSession, viewsFromSession);
      printSessionPriceDifference(sessionsFromCustomer, viewsFromSession, buysFromSession);
      printCustomerItemViewsForPurchase(sessionsFromCustomer, viewsFromSession, buysFromSession);
      lookedAt(sessionsFromCustomer, viewsFromSession);
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
