public class App {

  public static void main(String[] args) throws Exception {
    Shop shop = new Shop("Shop 1", "Address 1");
    String address = new String("Address 2");
    System.out.println(shop.getName());
    System.out.println(shop.setShopDetails(address, Shop::setAddress));
    System.out.println("Hello, World!");
  }
}
