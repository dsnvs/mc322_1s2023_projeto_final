/*
 * Each permission is a pair of a resource type and a permission type.
 * This class is used to represent the permissions granted to an access token.
 * It is also used to represent the permissions required to perform an action.
 */

public class Permission {
  private ResourceType resourceType;
  private PermissionType permissionType;

  private enum PermissionType {
    PUBLIC(0),
    READ(1),
    WRITE(2);

    private int value;

    private PermissionType(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  public Permission(ResourceType resourceType, PermissionType permissionType) {
    this.resourceType = resourceType;
    this.permissionType = permissionType;
  }

  public ResourceType getResourceType() {
    return resourceType;
  }

  public PermissionType getPermissionType() {
    return permissionType;
  }

  // Returns true if this permission is satisfied by the given permission.
  // For example, a permission to read a product is satisfied by a permission to write a product.

  public boolean isSatisfiedBy(Permission permission) {
    return this.resourceType == permission.resourceType &&
        this.permissionType.getValue() >= permission.permissionType.getValue();
  }

  // All objects should have fromString and toString method so that they can be easily serialized.

  public static Permission fromString(String permissionString) {
    String[] parts = permissionString.split(":");
    if (parts.length != 2) {
      throw new IllegalArgumentException("Invalid permission string: " + permissionString);
    }

    ResourceType resourceType = ResourceType.fromPrefix(parts[0]);
    PermissionType permissionType = PermissionType.valueOf(parts[1].toUpperCase());
    return new Permission(resourceType, permissionType);
  }

  @Override
  public String toString() {
    return resourceType.prefix + ":" + permissionType.toString().toLowerCase();
  }
}
