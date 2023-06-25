/*
 * This enum is used to represent the types of resources used in the app.
 */

public enum ResourceType {
  PRODUCT("product"),
  VARIANT("variant"),
  COLLECTION("collection"),
  INVENTORYITEM("inventory_item");

  // The prefix is used as a type identifier for resource references.
  // Particularly relevant to make input easier for API consumers.

  public final String prefix;

  ResourceType(String prefix) {
    this.prefix = prefix;
  }

  public String getPrefix() {
    return prefix;
  }

  public static ResourceType fromPrefix(String prefix) {
    for (ResourceType type : ResourceType.values()) {
      if (type.prefix.equals(prefix)) {
        return type;
      }
    }

    // If no resource type matches the prefix, throw an exception.
    throw new IllegalArgumentException("No resource type with prefix " + prefix);
  }

  // All objects should have fromString and toString method so that they can be easily serialized.

  public static ResourceType fromString(String resourceTypeString) {
    String[] parts = resourceTypeString.split(":");
    if (parts.length != 2) {
      throw new IllegalArgumentException("Invalid resource type string: " + resourceTypeString);
    }

    return fromPrefix(parts[0]);
  }

  @Override
  public String toString() {
    return prefix;
  }
}
