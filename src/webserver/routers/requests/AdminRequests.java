package webserver.routers.requests;

public abstract class AdminRequests implements Request {
  private String shopName;
  private String userAccessTokenString;

  public String getShopName() {
    return shopName;
  }

  public String getUserAccessTokenString() {
    return userAccessTokenString;
  }

  public class GetShopDetailsRequest extends AdminRequests {

  };

  public class CreateShopRequest extends AdminRequests {
    private String address;

    public String getAddress() {
      return address;
    }
  }

  public class ChangeShopDetailsRequest extends AdminRequests {
    private String address;

    public String getAddress() {
      return address;
    }
  }
}
