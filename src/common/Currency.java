package common;

/*
 * This enum is used to represent a currency.
 * It is used both in the Money class to represent the currency of a given monetary value,
 * and in the Country class to represent the currency of a given country.
 */

public enum Currency {
  USD,
  BRL,
  EUR,
  GBP,
  JPY,
  CNY,
  CAD;

  /*
   * FWIW, this is a limited implementation.
   * A more complete solution for this would be to have a template string for each currency,
   * and then use that template to format the monetary value.
   * For example, the template for USD could be "$%d.%02d", and the template for JPY could be "%d".
   * This would allow us to support more currencies, and also to support currencies that use different
   * symbols for negative values (e.g., parentheses instead of a minus sign).
   * Also would allow us to deal with currencies where the symbol is not at the beginning of the string (e.g., EUR)
   */

  private static enum CurrencyFormat {

    /*
     * symbol: the symbol used for a given currency
     */
    USD("$", 2, '.'),
    BRL("R$", 2, ','),
    EUR("€", 2, ','),
    GBP("£", 2, '.'),
    JPY("¥", 0, '.'),
    CNY("¥", 2, '.'),
    CAD("$", 2, '.');

    private String symbol;
    private int decimalPlaces;
    private char decimalSeparator;

    private CurrencyFormat(String symbol, int decimalPlaces, char decimalSeparator) {
      this.symbol = symbol;
      this.decimalPlaces = decimalPlaces;
      this.decimalSeparator = decimalSeparator;
    }

    public String getSymbol() {
      return symbol;
    }

    public int getDecimalPlaces() {
      return decimalPlaces;
    }

    public char getDecimalSeparator() {
      return decimalSeparator;
    }
  }

  /*
   * Format takes a currency and a monetary value and returns a string representation of the value.
   * For example, if the currency is USD and the value is 12345, the formatter should return "$123.45".
   */

  public static String format(Currency currency, int value) {
    CurrencyFormat currencyFormat = CurrencyFormat.valueOf(currency.name());
    String symbol = currencyFormat.getSymbol();
    int decimalPlaces = currencyFormat.getDecimalPlaces();
    char decimalSeparator = currencyFormat.getDecimalSeparator();

    String valueString = String.valueOf(value);
    if (valueString.length() <= decimalPlaces) {
      valueString = "0".repeat(decimalPlaces - valueString.length() + 1) + valueString;
    }
    return symbol + valueString.substring(0, valueString.length() - decimalPlaces) +
        decimalSeparator + valueString.substring(valueString.length() - decimalPlaces);
  }

  // This method is used to parse a string representation of a currency.

  public static Currency fromString(String s) {
    return Currency.valueOf(s);
  }
}
