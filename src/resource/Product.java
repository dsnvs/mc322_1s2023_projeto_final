package resource;

import util.Visibility;

public class Product extends ResourcePrototype implements PublicView<Product> {
  public static final ResourceType type = ResourceType.PRODUCT;
  
  @PublicField
  private String name;
  @PublicField
  private String description;
  @PublicField
  private String vendor;
  @PublicField
  private String productType;
  private String tags;

  public Product(String shopName, String name, String description, String vendor, String productType, String tags) {
    super(type, shopName, Visibility.PUBLIC);
    this.name = name;
    this.description = description;
    this.vendor = vendor;
    this.productType = productType;
    this.tags = tags;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getVendor() {
    return vendor;
  }

  public String getProductType() {
    return productType;
  }

  public String getTags() {
    return tags;
  }
}
