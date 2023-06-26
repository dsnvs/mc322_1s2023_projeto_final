package server;

import java.util.ArrayList;
import java.util.List;

import webserver.common.UserAccessToken;

import util.AccessToken;
import util.GlobalID;
import util.Permission;

public class Shop {
  // The name is the global ID of the shop.
  private final String name;
  private String address;
  private List<AccessToken> accessToken;
  private List<GlobalID> products;
  private List<GlobalID> collections;
  private List<GlobalID> orders;

  public Shop(String name, String address, AccessToken adminAccessToken) {
    this.name = name;
    this.address = address;

    this.accessToken = new ArrayList<AccessToken>();
    this.accessToken.add(adminAccessToken);

    this.products = new ArrayList<GlobalID>();
    this.collections = new ArrayList<GlobalID>();
    this.orders = new ArrayList<GlobalID>();
  }

  public void addProduct(GlobalID productID) {
    this.products.add(productID);
  }

  public void addCollection(GlobalID collectionID) {
    this.collections.add(collectionID);
  }

  public void addOrder(GlobalID orderID) {
    this.orders.add(orderID);
  }

  public void setAddress(String address) {
    this.address = address;
  }

  protected void setAccessToken(List<AccessToken> accessToken) {
    this.accessToken = accessToken;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  protected List<AccessToken> getAccessToken() {
    return accessToken;
  }

  private AccessToken findAccessToken(String publicKey) {
    for (AccessToken token : accessToken) {
      if (token.getPublicKey().equals(publicKey)) {
        return token;
      }
    }
    return null;
  }

  public boolean isAdmin(UserAccessToken userAccessToken) {
    AccessToken internalAccessToken = findAccessToken(userAccessToken.getPublicKey());
    if (internalAccessToken != null && internalAccessToken.validatePrivateKey(userAccessToken.getPrivateKey())) {
      return internalAccessToken.isAdmin();
    } else {
      return false;
    }
  }

  public boolean hasPermission(UserAccessToken userAccessToken, Permission permission) {
    AccessToken internalAccessToken = findAccessToken(userAccessToken.getPublicKey());
    if (internalAccessToken != null && internalAccessToken.validatePrivateKey(userAccessToken.getPrivateKey())) {
      return internalAccessToken.hasPermission(permission);
    } else {
      return false;
    }
  }
}
