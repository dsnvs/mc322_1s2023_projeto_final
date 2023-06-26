import com.google.gson.Gson;

import server.Server;
import util.AccessToken.AccessTokenWithPrivateKey;

public class App {

  public static void main(String[] args) throws Exception {
    Gson gson = new Gson();
    Server server = new Server();
    AccessTokenWithPrivateKey adminToken = server.createShop("Shop 1", "Address 1");

    System.out.println(adminToken.getPrivateKey());
    System.out.println(adminToken.getAccessToken());
    System.out.println(server.findShop("Shop 1").getName());
    System.out.println(gson.toJson(server.findShop("Shop 1")));




/*

    // Shop shop = new Shop("Shop 1", "Address 1");
    String json = gson.toJson(shop);
    System.out.println(json);
    Shop shop2 = gson.fromJson(json, Shop.class);
    System.out.println(shop2.getName());
    // String address = new String("Address 2");
    System.out.println(shop.getName());
    // System.out.println(shop.setShopDetails(address, Shop::setAddress));
    // System.out.println("Hello, World!");

     */
  }
}
