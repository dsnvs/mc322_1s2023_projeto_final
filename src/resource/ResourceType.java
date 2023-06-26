package resource;
import java.util.Arrays;

/*
 * This enum is used to represent the types of resources used in the app.
 */

public enum ResourceType {
  PRODUCT,
  VARIANT,
  COLLECTION,
  INVENTORYITEM,
  ORDER;

  public static boolean isValidResourceType(String resourceType) {
    for (ResourceType type : ResourceType.values()) {
      if (type.name().equals(resourceType)) {
        return true;
      }
    }
    return false;
  }

  // This method is used to find the resource type from the given name.
  // It is pretty much a `fromString()` method, but given this is an enum, I have named it `findByName`.

  public static ResourceType findByName(String name) throws IllegalArgumentException {
    return Arrays.stream(ResourceType.values())
        .filter(value -> value.name().equals(name))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid resource type: " + name));
  }

  // `toString()` method is used to convert the object to a string.
  // Facilitates serialization across the app.

  @Override
  public String toString() {
    return name();
  }
}
