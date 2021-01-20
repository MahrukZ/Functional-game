package com.cm6123.snl.dice;

import java.util.ArrayList;
import java.util.List;

public class LoadedDiceFactory implements DiceFactory {

  private Integer[] values;

  public LoadedDiceFactory(final Integer... theValues) {
    this.values = theValues;
  }


  @Override
  public List<Dice> makeDice(final Integer faces, final Integer count) {
    return makeDice(faces, count, values);
  }

  public List<Dice> makeDice(final Integer faces, final Integer count, final Integer... someValues) {

    if (count != someValues.length) {
      throw new IllegalArgumentException("Number of values provided doesn't match the stated number of dice.");
    }
    List<Dice> dices = new ArrayList<>();

    for (Integer value : someValues) {
      if (value > faces) {
        throw new IllegalArgumentException("Loaded Value is greater than the number of faces.");
      }
      dices.add(new LoadedDice(value));
    }

    return dices;
  }
}
