package webserver.middleware;

import server.Server;
import server.Shop;
import util.Permission;
import webserver.common.UserAccessToken;

/*
 * Checks if the user has permission to access a given resource.
 */

public class ResourceAccessMiddleware {
    static boolean hasPermission(Server server, String userAccessTokenString, Permission permission) {
        try {
            UserAccessToken userAccessToken = UserAccessToken.fromString(userAccessTokenString);
            Shop shop = server.findShop(userAccessToken.getShopName());
            if (shop == null) {
                return false;
            } else {
                return shop.hasPermission(userAccessToken, permission);
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
