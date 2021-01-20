package com.cm6123.snl.dice;

import java.util.ArrayList;
import java.util.List;

public final class RandomDiceFactory implements DiceFactory {

  @Override
  public List<Dice> makeDice(final Integer faces, final Integer count) {
    List<Dice> dices = new ArrayList<>();

    for (Integer value = 0; value < count; value++) {
      dices.add(new RandomDice(faces));
    }

    return dices;
  }
}
