import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * This class represents an access token that is used to access a resource within a shop.
 * It contains the public and private keys of the access token, the shop name, and the permissions granted to the access token.
 * It also contains a method to check if the access token has the required permissions to perform an action.
 * Finally it contains a method that generates a new access token
 */

public class AccessToken {
  
  // The separator is used for a string representation of an access token.
  // I use this particular string as the separator because it should be invalid for any of the fields.

  private static final String SEPARATOR = "-AccessToken-";
  private final String publicKey;
  private final String shopName;
  private final String hashedPrivateKey;
  private final boolean isAdmin;
  private List<Permission> permissions;

  public static class AccessTokenWithPrivateKey {
    private final AccessToken accessToken;
    private final String privateKey;

    AccessTokenWithPrivateKey(AccessToken accessToken, String privateKey) {
      this.accessToken = accessToken;
      this.privateKey = privateKey;
    }

    public AccessToken getAccessToken() {
      return accessToken;
    }

    public String getPrivateKey() {
      return privateKey;
    }
  }

  private AccessToken(String shopName, List<Permission> permissions, String publicKey, String hashedPrivateKey, boolean isAdmin) {
    this.shopName = shopName;
    this.permissions = permissions;
    this.publicKey = publicKey;
    this.hashedPrivateKey = hashedPrivateKey;
    this.isAdmin = isAdmin;
  }

  public static AccessTokenWithPrivateKey generateAccessToken(String shopName, List<Permission> permissions, boolean isAdmin) {
    String publicKey = generatePublicKey();
    String privateKey = generatePrivateKey(publicKey);
    String hashedPrivateKey = hashPrivateKey(privateKey);
    AccessToken accessToken = new AccessToken(shopName, permissions, publicKey, hashedPrivateKey, isAdmin);
    return new AccessTokenWithPrivateKey(accessToken, privateKey);
  }

  private static String generatePublicKey() {
    int length = 10;
    String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "0123456789"
        + "abcdefghijklmnopqrstuvxyz";

    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int index = (int) (alphaNumericString.length() * Math.random());
      sb.append(alphaNumericString.charAt(index));
    }

    return sb.toString();
  }

  private static String generatePrivateKey(String publicKey) {
    String reversedPublicKey = new StringBuilder(publicKey).reverse().toString();
    return reversedPublicKey + (new Random().nextInt(100) + 1);
  }

  private static String hashPrivateKey(String privateKey) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hashedBytes = digest.digest(privateKey.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for (byte b : hashedBytes) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public String getPublicKey() {
    return this.publicKey;
  }

  public String getShopName() {
    return this.shopName;
  }

  public List<Permission> getPermissions() {
    return this.permissions;
  }

  public boolean isAdmin() {
    return this.isAdmin;
  }

  public boolean hasPermission(Permission requiredPermission) {
    return this.permissions.contains(requiredPermission);
  }

  public boolean validatePrivateKey(String privateKey) {
    return this.hashedPrivateKey.equals(hashPrivateKey(privateKey));
  }


    @Override
    public String toString() {
        String permissionsString = permissions.stream()
            .map(Permission::toString)
            .collect(Collectors.joining(","));

        return publicKey + SEPARATOR + shopName + SEPARATOR + hashedPrivateKey + SEPARATOR + isAdmin + SEPARATOR + permissionsString;
    }

    public static AccessToken fromString(String tokenString) {
        String[] parts = tokenString.split(SEPARATOR);
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid token string");
        }

        String publicKey = parts[0];
        String shopName = parts[1];
        String hashedPrivateKey = parts[2];
        boolean isAdmin = Boolean.parseBoolean(parts[3]);
        List<Permission> permissions = Arrays.stream(parts[4].split(","))
            .map(Permission::fromString)
            .collect(Collectors.toList());

        return new AccessToken(shopName, permissions, publicKey, hashedPrivateKey, isAdmin);
    }
}
