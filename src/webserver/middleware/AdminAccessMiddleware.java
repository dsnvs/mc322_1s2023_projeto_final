package webserver.middleware;

import server.Server;
import server.Shop;
import webserver.common.UserAccessToken;

/*
 * Checks if the user has permission to access the admin settings for a shop.
 */

public class AdminAccessMiddleware {
  static boolean isAdmin(Server server, String userAccessTokenString) {
    try {
      UserAccessToken userAccessToken = UserAccessToken.fromString(userAccessTokenString);
      Shop shop = server.findShop(userAccessToken.getShopName());
      if (shop == null) {
        return false;
      } else {
        return shop.isAdmin(userAccessToken);
      }
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
