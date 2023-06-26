package util;

import java.util.Random;

import resource.ResourceType;
import resource.PublicView.PublicField;

/*
 * The format of a global ID is as follows:
 * <shop name>:<resource type>:<12-digit ID>
 */

public class GlobalID {
  private static Random rand = new Random();
  @PublicField
  private final String value;
  
  /*
   * This constructor is used when loading the global ID from the database.
   */

  public GlobalID(String value) {
    if (isValidGlobalIDFormat(value)) {
      this.value = value;
    } else {
      throw new IllegalArgumentException("Invalid global ID format.");
    }
  }

  /*
   * This method is used to get the resource type from the global ID.
   * Not sure if it is going to be useful, but I have added it anyway.
   */

  public ResourceType getResourceType() {
    String[] parts = value.split(":");
    return ResourceType.findByName(parts[1]);
  }

  /*
   * This constructor is used when creating a new resource.
   */

  public GlobalID(ResourceType type, String shopName) {
    long timestamp = System.currentTimeMillis() % 1_000_000; // Getting last 6 digits of the current timestamp
    int random = rand.nextInt(1_000_000); // Generating a 6-digit random number

    // Combine the timestamp and random number to get a 12-digit ID
    String id = String.format("%06d%06d", timestamp, random);
    this.value = shopName + ":" + type + ":" + id;
  }

  public String getValue() {
    return value;
  }

  /*
   * This method is used to check if the given global ID is valid.
   * It checks if the global ID is in the correct format.
   */

  public static boolean isValidGlobalIDFormat(String globalID) {
    String[] parts = globalID.split(":");
    if (parts.length == 3 && ResourceType.isValidResourceType(parts[1])) {
      return true;
    } else {
      return false;
    }
  }
}