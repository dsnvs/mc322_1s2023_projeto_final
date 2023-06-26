package webserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import server.Server;

public class WebServer {
  public static void main(String[] args) throws IOException {
    Server server = new Server();
    com.sun.net.httpserver.HttpServer httpServer = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(8000), 0);
    httpServer.createContext("/admin", new webserver.routers.AdminRouter(server, "/admin"));
    // server.createContext("/api", new webserver.routers.ApiRouter());
    // server.createContext("/static", new webserver.routers.StaticRouter());
    httpServer.setExecutor(null); // creates a default executor
    httpServer.start();
  }  
}
