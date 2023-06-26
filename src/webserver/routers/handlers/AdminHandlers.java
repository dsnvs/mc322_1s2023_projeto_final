package webserver.routers.handlers;

import java.util.function.BiFunction;

import server.Server;
import server.Shop;
import util.AccessToken.AccessTokenWithPrivateKey;
import webserver.middleware.JsonMiddleware;
import webserver.routers.requests.AdminRequests;
import webserver.routers.requests.AdminRequests.ChangeShopDetailsRequest;
import webserver.routers.requests.AdminRequests.CreateShopRequest;
import webserver.routers.requests.AdminRequests.GetShopDetailsRequest;

public enum AdminHandlers implements Handler {
  GET_SHOP_DETAILS("/get_shop_details", "GET", true, (Server server, GetShopDetailsRequest req) -> {
    Shop shop = server.findShop(req.getShopName());
    if (shop == null) {
      return "Shop not found";
    } else {
      return JsonMiddleware.toJson(shop);
    }
  }),

  CREATE_SHOP("/create_shop", "POST", true, (Server server, CreateShopRequest req) -> {
    try {
      AccessTokenWithPrivateKey accessTokenWithPrivateKey = server.createShop(req.getShopName(), req.getAddress());
      return JsonMiddleware.toJson(accessTokenWithPrivateKey);
    } catch (Exception e) {
      return "Shop already exists";
    }
  }),

  CHANGE_SHOP_DETAILS("/change_shop_details", "POST", true, (Server server, ChangeShopDetailsRequest req) -> {
    Shop shop = server.findShop(req.getShopName());

    if (shop == null) {
      return "Shop not found";
    } else {
      return JsonMiddleware.toJson(shop);
    }
  });

  private final String handle;
  private final String method;
  private final boolean forAdmin;
  private final BiFunction<Server, ? extends AdminRequests , String> handler;

  private <T extends AdminRequests> AdminHandlers(String handle, String method, boolean forAdmin,
      BiFunction<Server, T, String> handler) {
    this.handle = handle;
    this.method = method;
    this.forAdmin = forAdmin;
    this.handler = handler;
  }

  public String getHandle() {
    return handle;
  }

  public String getMethod() {
    return method;
  }

  public boolean isForAdmin() {
    return forAdmin;
  }

  public BiFunction<Server, ? extends AdminRequests, String> getHandler() {
    return handler;
  }
}
