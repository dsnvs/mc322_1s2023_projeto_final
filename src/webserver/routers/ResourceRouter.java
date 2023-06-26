package webserver.routers;

import java.security.Permission;
import java.util.List;
import java.util.function.Function;

import server.Server;

public abstract class ResourceRouter {
  abstract String handle(String body);

  interface Endpoint {
    List<Endpoint> getAllEndpoints();
  }
}
