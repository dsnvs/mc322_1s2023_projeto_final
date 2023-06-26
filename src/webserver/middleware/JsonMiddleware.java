package webserver.middleware;

import com.google.gson.Gson;

import webserver.routers.requests.Request;

/*
 * Takes an object and returns its json representation.
 */

public class JsonMiddleware {
  static final Gson gson = new Gson();

  public static <T> String toJson(T object) {
    return gson.toJson(object);
  }

  static public <T extends Request> T fromJson(String json, Class<T> classOfT) {
    return gson.fromJson(json, classOfT);
  }
}
