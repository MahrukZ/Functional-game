package com.cm6123.snl.dice;

public class LoadedDice implements Dice {

  private Integer value;

  public LoadedDice(final Integer aValue) {
    this.value = aValue;
  }

  @Override
  public Integer roll() {
    return value;
  }
}
