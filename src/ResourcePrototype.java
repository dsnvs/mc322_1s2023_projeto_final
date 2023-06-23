public abstract class ResourcePrototype {
  public final ResourceType type;
  public final String id;

  public ResourcePrototype(ResourceType type, String id) {
    this.type = type;
    this.id = id;
  }
}
