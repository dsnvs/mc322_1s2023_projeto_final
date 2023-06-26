package webserver.routers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

import server.Server;

public class AdminRouter extends Router {

  public AdminRouter(Server server, String path) {
    super(server, path);
  }

  /*
   * This method handles the admin page request.
   */

  @Override
  public void handle(HttpExchange t) throws IOException {
    String response = "Hello from the admin page!";
    t.sendResponseHeaders(200, response.length());
    OutputStream os = t.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }
}