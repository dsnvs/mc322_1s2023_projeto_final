package server;
import java.util.ArrayList;
import java.util.List;

import util.AccessToken;
import util.AccessToken.AccessTokenWithPrivateKey;

public class Server {
  private List<String> locks;
  private List<Shop> shops;

  public class NameAlreadyExistsException extends Exception {
    public NameAlreadyExistsException(String message) {
      super(message);
    }
  }

  // Should implement a way to retrieve all the shops in the server from a file.
  // Should implement a way to save all the shops in the server to a file.


  public Server() {
    locks = new ArrayList<>();
    shops = new ArrayList<>();
  }

  public Shop findShop(String name) {
    for (Shop shop : shops) {
      if (shop.getName().equals(name)) {
        return shop;
      }
    }
    return null;
  }

  public boolean shopExists(String name) {
    return findShop(name) != null;
  }

  public AccessTokenWithPrivateKey createShop(String name, String address) throws NameAlreadyExistsException{

    // Should have a check to see if shop name already exists.

    if (shopExists(name)) {
      throw new NameAlreadyExistsException("Shop name already exists.");
    }
    
    locks.add(name);
    AccessTokenWithPrivateKey adminAccessTokenWithPrivateKey = AccessToken.generateAdminAccessToken(name);
    System.out.println("AdminPrivateKey" + adminAccessTokenWithPrivateKey.getPrivateKey());

    shops.add(new Shop(name, address, adminAccessTokenWithPrivateKey.getAccessToken()));
    locks.remove(name);
    return adminAccessTokenWithPrivateKey;
  }

}