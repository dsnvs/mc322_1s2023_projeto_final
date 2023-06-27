package webserver.routers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

import server.Server;
import webserver.routers.handlers.AdminHandlers;

public class AdminRouter extends Router {

  public AdminRouter(Server server, String path) {
    super(server, path);
  }

  /*
   * This method handles the admin page request.
   */

  @Override
  public void handle(HttpExchange t) throws IOException {
    try {
      String handle = t.getRequestURI().toString().split("/")[2];
      String method = t.getRequestMethod();

      if (handle == null || method == null) {
        t.sendResponseHeaders(400, 0);
        return;
      }

      AdminHandlers handler = AdminHandlers.getHandler(handle, method);

      String body = new String(t.getRequestBody().readAllBytes());

      String response = handler.handle(getServer(), body);
      
      System.out.println(response);
      t.getResponseHeaders().add("Content-Type", "application/json");
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}