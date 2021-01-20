package com.cm6123.snl.dice;

public class RandomDice implements Dice {
  /**
   * the number of faces on the Dice.
   */
  private Integer faces;

  /**
   * Create a Dice with the specified number of faces.
   *
   * @param howManyFaces number of faces on the dice
   */
  public RandomDice(final Integer howManyFaces) {
    this.faces = howManyFaces;
  }

  /**
   * roll the dice.
   * @return a number between 1 and the number of faces inclusive.
   */
  public Integer roll() {
    return Long.valueOf(1 + Math.round(Math.floor(Math.random() * faces))).intValue();
  }
}
