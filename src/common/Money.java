package common;

import resource.PublicView.PublicField;

/*
 * This class is used to represent a monetary value.
 * It should be the only.
 */

public class Money {
  @PublicField
  private Currency currency;

  /* 
   * This is the monetary value, in cents.
   * We use ints because ints are way better than floats for monetary values,
   * In a real scenario, this could be an annoyance when exporting money to an external application.
   */
  
  @PublicField
  private int value;

  public Money(Currency currency, int value) {
    this.currency = currency;
    this.value = value;
  }

  public Money(String s) {
    this.currency = Currency.fromString(s.substring(0, 3));
    this.value = Integer.parseInt(s.substring(3));
  }

  public Currency getCurrency() {
    return currency;
  }

  public int getValue() {
    return value;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String format() {
    return Currency.format(currency, value);
  }

  public Money add(Money other) {
    if (currency != other.currency) {
      throw new IllegalArgumentException("Cannot add money of different currencies");
    }
    return new Money(currency, value + other.value);
  }

  public Money subtract(Money other) {
    if (currency != other.currency) {
      throw new IllegalArgumentException("Cannot subtract money of different currencies");
    }
    return new Money(currency, value - other.value);
  }

  public Money multiply(int factor) {
    return new Money(currency, value * factor);
  }
  
  public Money divide(int factor) {
    return new Money(currency, value / factor);
  }

  public Money fromString(String s) {
    
    return new Money(currency, Integer.parseInt(s));
  }

  @Override
  public Money clone() {
    return new Money(currency, value);
  }

  @Override
  public String toString() {
    return currency.name() + Integer.toString(value);
  }

  @Override
  public boolean equals(Object other) {
    if (other == null || !(other instanceof Money)) {
      return false;
    }
    Money otherMoney = (Money) other;
    return currency == otherMoney.currency && value == otherMoney.value;
  }
  
}
