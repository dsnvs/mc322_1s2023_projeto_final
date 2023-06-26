package resource;
import resource.PublicView.PublicField;
import util.GlobalID;
import util.Visibility;

// This class is the base class for all resources.
// Maybe it should be called Resource instead of ResourcePrototype.

public abstract class ResourcePrototype {
  @PublicField
  public final ResourceType type;
  @PublicField
  public final GlobalID globalID;
  public Visibility visibility;
  public Status status;

  // This enum is used to represent the status of the resource.
  // It might seem redudant since we already have the visibility field, but it is not.
  // Visibility limits the access to the resource from an administrative perspective,
  // whereas status limits the access to the resource from a customer perspective.
  // For example, a product can be visible to the admin, but not visible to the customer.
  // In more practical terms, you could prepare a product for sale, but want to publish it only on a later date.

  private enum Status {
    PUBLISHED,
    UNLISTED,
    DRAFT;
  }

  public ResourcePrototype(ResourceType type, String shopName, Visibility visibility) {
    this.type = type;
    this.globalID = new GlobalID(type, shopName);
    this.visibility = visibility;
  }

  public GlobalID getGlobalID() {
    return globalID;
  }

  public ResourceType getType() {
    return type;
  }

  public Visibility getVisibility() {
    return visibility;
  }

  public void setVisibility(Visibility visibility) {
    this.visibility = visibility;
  }

  public boolean isActionable() {
    return status == Status.PUBLISHED;
  }

  public boolean canBeShown() {
    return visibility == Visibility.PUBLIC && status == Status.PUBLISHED || status == Status.UNLISTED;
  }

  public boolean canBeListed() {
    return visibility == Visibility.PUBLIC && status == Status.PUBLISHED;
  }
}
