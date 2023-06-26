package server;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.AccessToken;
import util.AccessToken.AccessTokenWithPrivateKey;

/*
 * Server is the main class of the server, it is responsible for managing all the shops.
 * It should be the only front-facing class of the app.
 */

public class Server {
  private List<String> locks;
  private List<Shop> shops;
  Gson gson = new Gson();

  /*
   * Exception used when trying to create a shop with a name already occupied.
   */

  public class NameAlreadyExistsException extends Exception {
    public NameAlreadyExistsException(String message) {
      super(message);
    }
  }

  public Server() {
    locks = new ArrayList<>();
    shops = new ArrayList<>();
    loadServer();
  }


  /*
   * Loads the server details from a SQLite database.
   * If the database does not exist, it creates one.
   */

  private void loadServer() {
    try (Connection dbConnection = DriverManager.getConnection("jdbc:sqlite:shops.db"); Statement statement = dbConnection.createStatement();) {
      
      statement.setQueryTimeout(10);

      Gson gson = new Gson();

      String sql = "CREATE TABLE IF NOT EXISTS shops (id TEXT PRIMARY KEY, object TEXT)";
      statement.executeUpdate(sql);

      ResultSet rs = statement.executeQuery("SELECT * FROM shops");
      while (rs.next()) {
        String id = rs.getString("id");
        String object = rs.getString("object");
        shops.add(gson.fromJson(object, Shop.class));
        System.out.println(id + " : " + object);
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  /*
   * This method should be called whenever a shop is added or updated on the server.
   * It updates the server file with the change.
   */

  private void putShop(Shop shop) {
    try (Connection dbConnection = DriverManager.getConnection("jdbc:sqlite:shops.db"); Statement statement = dbConnection.createStatement();) {
      statement.setQueryTimeout(10);

      String sql = "INSERT INTO shops VALUES ('" + shop.getName() + "', '" + gson.toJson(shop) + "')";
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  /*
   * This method updates a shop with new details.
   */

  public void updateShop(String name, String address) {
    Shop shop = findShop(name);
    shop.setAddress(address);
    putShop(shop);
  }

  /*
   * This method adds a new shop to the server.
   */

  private void addShop(Shop shop) {
    shops.add(shop);
    putShop(shop);
  }

  /*
   * This method returns a shop with the given name.
   */

  public Shop findShop(String name) {
    for (Shop shop : shops) {
      if (shop.getName().equals(name)) {
        return shop;
      }
    }
    return null;
  }

  /*
   * This method if a shop with a given name exists in the server.
   */

  public boolean shopExists(String name) {
    return findShop(name) != null;
  }

  /*
   * This method creates a new shop with the given name and address.
   */

  
  public AccessTokenWithPrivateKey createShop(String name, String address) throws NameAlreadyExistsException {
    if (shopExists(name) || locks.contains(name)) {
      throw new NameAlreadyExistsException("Shop name already exists.");
    }

    locks.add(name);
    AccessTokenWithPrivateKey adminAccessTokenWithPrivateKey = AccessToken.generateAdminAccessToken(name);
    System.out.println("AdminPrivateKey" + adminAccessTokenWithPrivateKey.getPrivateKey());

    addShop(new Shop(name, address, adminAccessTokenWithPrivateKey.getAccessToken()));
    locks.remove(name);
    return adminAccessTokenWithPrivateKey;
  }

}