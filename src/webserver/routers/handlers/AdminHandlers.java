package webserver.routers.handlers;

import java.util.function.BiFunction;

import server.Server;
import server.Shop;
import util.AccessToken.AccessTokenWithPrivateKey;
import webserver.middleware.JsonMiddleware;
import webserver.routers.requests.AdminRequests;
import webserver.routers.requests.Request;
import webserver.routers.requests.AdminRequests.ChangeShopDetailsRequest;
import webserver.routers.requests.AdminRequests.CreateShopRequest;
import webserver.routers.requests.AdminRequests.GetShopDetailsRequest;

public enum AdminHandlers implements Handler {
  GET_SHOP_DETAILS("get_shop_details", "POST", true, (Server server, GetShopDetailsRequest req) -> {
    Shop shop = server.findShop(req.getShopName());
    if (shop == null) {
      return "Shop not found";
    } else {
      String json = JsonMiddleware.toJson(shop);
      return json;
    }
  }, GetShopDetailsRequest.class),

  CREATE_SHOP("create_shop", "POST", true, (Server server, CreateShopRequest req) -> {
    try {
      AccessTokenWithPrivateKey accessTokenWithPrivateKey = server.createShop(req.getShopName(), req.getAddress());
      return JsonMiddleware.toJson(accessTokenWithPrivateKey);
    } catch (Exception e) {
      return "Shop already exists";
    }
  }, CreateShopRequest.class),

  CHANGE_SHOP_DETAILS("change_shop_details", "POST", true, (Server server, ChangeShopDetailsRequest req) -> {
    Shop shop = server.findShop(req.getShopName());

    if (shop == null) {
      return "Shop not found";
    } else {
      return JsonMiddleware.toJson(shop);
    }
  }, ChangeShopDetailsRequest.class);
  
  final String handle;
  final String method;
  final boolean forAdmin;
  final Class<? extends AdminRequests> requestClass;
  final BiFunction<Server, ? extends AdminRequests, String> handler;

  private <T extends AdminRequests> AdminHandlers(String handle, String method, boolean forAdmin,
      BiFunction<Server, T, String> handler, Class<T> requestClass) {
    this.handle = handle;
    this.method = method;
    this.forAdmin = forAdmin;
    this.handler = handler;
    this.requestClass = requestClass;
  }

  public static AdminHandlers getHandler(String handle, String method) {
    for (AdminHandlers handler : AdminHandlers.values()) {
      if (handler.handle.equals(handle) && handler.method.equals(method)) {
        return handler;
      }
    }
    return null;
  }

  /*
   * This is unchecked because Java doesn't support generic enums.
   * It's safe because the requestClass is always the same as the handler's.
   * TRUST ME BRO
   */

  
  @SuppressWarnings("unchecked")
  public <T extends Request> String handle(Server server, String body) {
    T req = (T) JsonMiddleware.fromJson(body, requestClass);
    BiFunction<Server, T, String> handler = (BiFunction<Server, T, String>) this.handler;
    return handler.apply(server, req);
  }
}
