package common;

import resource.PublicView.PublicField;

/*
 * Used to represent an image across the server, both in resources and outside of resources.
 * Image URL is immutable because I don't think you should re-use an instance, and should instead create a new one.
 */

public class Image {
  @PublicField
  private final String url;
  @PublicField
  private String alt;

  public Image(String url) {
    this(url, "");
  }

  public Image(String url, String alt) {
    this.url = url;
    this.alt = alt;
  }

  public String getUrl() {
    return url;
  }

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

  @Override
  public Image clone() {
    return new Image(url, alt);
  }
}
