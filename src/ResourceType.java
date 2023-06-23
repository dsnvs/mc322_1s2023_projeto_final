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

  private ResourceType(String prefix) {
    this.prefix = prefix;
  }
}
