package resource;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/*
 * This interfaces defines which objects can be viewed without an AccessToken.
 * And also defines which fields of the object can be viewed without an AccessToken.
 * The bounds here are that the object should be a ResourcePrototype.
 */

public interface PublicView<T extends ResourcePrototype> {
  public @interface PublicField {
    // This is a marker interface.
    // It is used to mark the fields that should be visible to the public.
    // This is used by the Gson library to serialize the object.
  };

  /*
   * This function returns a Gson ExclusionStrategy object.
   * This ExclusionStrategy explicitly tells Gson to serialize only
   * the fields that are marked with the PublicField annotation.
   */

  static ExclusionStrategy getExclusionStrategy() {
    return new ExclusionStrategy() {
      @Override
      public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(PublicField.class) == null;
      }

      @Override
      public boolean shouldSkipClass(Class<?> clazz) {
        return false;
      }
    };

  }
}