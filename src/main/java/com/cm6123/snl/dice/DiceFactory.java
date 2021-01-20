package com.cm6123.snl.dice;

import java.util.List;

/**
 * Factory that creates a set of Dice.
 */

public interface DiceFactory {
  /**
   * Make a set of Dice.  There will be the specified number
   * of Dice and each dice has the same number of faces.
   *
   * @param faces - how many faces on the dice
   * @param count - how many dice in the set
   * @return - the list of Dice
   */
  List<Dice> makeDice(Integer faces, Integer count);
}
