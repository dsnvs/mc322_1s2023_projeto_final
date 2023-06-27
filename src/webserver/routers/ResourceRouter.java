package webserver.routers;

import java.util.List;

public abstract class ResourceRouter {
  abstract String handle(String body);

  interface Endpoint {
    List<Endpoint> getAllEndpoints();
  }
}
