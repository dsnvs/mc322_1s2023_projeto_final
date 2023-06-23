import java.util.Random;

public class GlobalID {
  private static Random rand = new Random();
  
  public static String createGlobalID(ResourceType type, String shopName) {
    long timestamp = System.currentTimeMillis() % 1_000_000; // Getting last 6 digits of the current timestamp
    int random = rand.nextInt(1_000_000); // Generating a 6-digit random number
    
    // Combine the timestamp and random number to get a 12-digit ID
    String id = String.format("%06d%06d", timestamp, random);
    return shopName + ":" + type + ":" + id;
  }
}