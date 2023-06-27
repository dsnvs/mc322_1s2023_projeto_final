package webserver.routers;

import com.sun.net.httpserver.HttpHandler;

import server.Server;

public abstract class Router implements HttpHandler {
  final String path;
  final Server server;

  Router(Server server, String path) {
    this.server = server;
    this.path = path;
  }

  Server getServer() {
    return server;
  }

  public String getPath() {
    return path;
  }
}
