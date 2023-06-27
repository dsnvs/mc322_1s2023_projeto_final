package webserver.routers.handlers;

import server.Server;
import webserver.routers.requests.Request;

/**
 * Marker interface for all handlers.
 */

public interface Handler {
  <T extends Request> String handle(Server server, String body);
}
