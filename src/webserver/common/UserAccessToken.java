package webserver.common;

public class UserAccessToken {
  private static final String SEPARATOR = "-AccessToken-";
  private final String publicKey;
  private final String shopName;
  private final String privateKey;

  UserAccessToken(String shopName, String publicKey, String privateKey) {
    this.shopName = shopName;
    this.publicKey = publicKey;
    this.privateKey = privateKey;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public String getShopName() {
    return shopName;
  }

  public String getPrivateKey() {
    return privateKey;
  }

  public static UserAccessToken fromString(String accessTokenString) throws IllegalArgumentException{
    String[] parts = accessTokenString.split(SEPARATOR);
    if (parts.length != 3) {
      throw new IllegalArgumentException("Invalid access token string");
    }
    return new UserAccessToken(parts[0], parts[1], parts[2]);
  }
}
