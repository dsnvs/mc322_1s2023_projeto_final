import java.util.List;
import java.util.function.Function;

public class Shop {
  // The name is the global ID of the shop.
  private final String name;
  private String address;
  private List<AccessToken> accessToken;

  public Shop(String name, String address) {
    this.name = name;
    this.address = address;
  }

  // I have used this as a private method because there should be 
  private Void setAddress(String address) {
    this.address = address;
  }

  private void setAccessToken(List<AccessToken> accessToken) {
    this.accessToken = accessToken;
  } 

  public String getName() {
    return name;
  }

  // This is a test method to check if the shop details can be set.

  public <T> boolean setShopDetails(T value, Function<T, Void> setter) {
    
    return true;
  }
 }
/*
  public AccessTokenWithPrivateKey generateAdminAccessToken() {
    AccessTokenWithPrivateKey accessTokenWithPrivateKey = AccessToken.generateAccessToken(name, permissions);
    accessToken.add(accessTokenWithPrivateKey.getAccessToken());
    return accessTokenWithPrivateKey;
  }
   */
}
