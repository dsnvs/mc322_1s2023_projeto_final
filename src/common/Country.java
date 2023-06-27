package common;

/*
 * This enum is used to represent a country.
 * It is used in the Shop class to represent the country of a given shop.
 */

public enum Country {
  USA(Currency.USD),
  BRAZIL(Currency.BRL),
  GERMANY(Currency.EUR),
  FRANCE(Currency.EUR),
  ENGLAND(Currency.GBP),
  JAPAN(Currency.JPY),
  CHINA(Currency.CNY),
  CANADA(Currency.CAD);

  private Currency currency;

  private Country(Currency currency) {
    this.currency = currency;
  }

  public Currency getCurrency() {
    return currency;
  }
}
