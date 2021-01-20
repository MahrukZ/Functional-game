package com.cm6123.snl.dice;


public class DiceResult {

  /**
   * The numeric value of rolling a set of Dice.
   */
  private final Integer value;
  /**
   * A boolean to state whether all the Dice have the
   * same value when the set is rolled.
   */
  private final Boolean areAllTheSame;

  /**
   * @param aValue     - the numeric sum of the separate Dice values.
   * @param allTheSame - true if all of the Dice have
   *                   the same numeric value when rolled.
   */
  public DiceResult(final Integer aValue, final Boolean allTheSame) {
    this.value = aValue;
    this.areAllTheSame = allTheSame;
  }

  /**
   * Value of the DiceSet roll.
   * @return the numerical sum of the Dice face values.
   */
  public Integer getValue() {
    return value;
  }

  /**
   * Indicates whether the Dice had the same values when rolled.
   * @return - true if all the Dice rolled the same value.
   */
  public Boolean areAllTheSame() {
    return areAllTheSame;
  }
}
