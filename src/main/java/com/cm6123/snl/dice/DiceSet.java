package com.cm6123.snl.dice;

import java.util.Iterator;
import java.util.List;

public class DiceSet {

  /**
   * List of Dice objects in the set.
   */
  private List<Dice> dice;

  /**
   * Construct a set of Dice.  Each dice has the same number of faces.
   *
   * @param faces - how many faces on each dice.
   * @param count - how many dice in the set.
   */
  public DiceSet(final Integer faces, final Integer count) {
    this(faces, count, new RandomDiceFactory());
  }

  /**
   * Construct a set of Dice providing a Factory to control the Dice.
   * Default factory is a RandomDiceFactory.
   * @param faces - how many faces on each Dice.
   * @param count - how many dice.
   * @param factory - factory that constrains the type of Dice included
   */
  public DiceSet(final Integer faces,
                 final Integer count,
                 final DiceFactory factory) {

    dice = factory.makeDice(faces, count);

  }

  /**
   * Tells the set of Dice to roll.
   * @return a DiceResult object
   */
  public DiceResult roll() {

    if (dice.size() == 1) {
      return new DiceResult(dice.get(0).roll(), Boolean.FALSE);
    }

    Boolean isDouble = true;
    Integer value = 0;
    Integer lastValue = 0;

    Iterator<Dice> dices = dice.iterator();
    Dice firstDice = dices.next();
    value += firstDice.roll();
    lastValue = value;

    while (dices.hasNext()) {
      Dice aDice = dices.next();
      Integer nextValue = aDice.roll();
      isDouble = isDouble && (lastValue.equals(nextValue));
      lastValue = nextValue;
      value += nextValue;
    }

    return new DiceResult(value, isDouble);

  }

}
