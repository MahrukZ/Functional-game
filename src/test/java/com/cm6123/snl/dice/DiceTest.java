package com.cm6123.snl.dice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DiceTest {

  @Test
  void rollOneDice() {

    Integer max = 0, min = 100;

    for (int i = 0; i < 1000; i++) {
      Integer roll = new RandomDice(6).roll();
      if (roll < min) {
        min = roll;
      }
      if (roll > max) {
        max = roll;
      }
    }

    Assertions.assertEquals("1,6", "" + min + "," + max);

  }

  @Test
  void checkDouble6() {

    DiceSet diceSet = new DiceSet(6, 2, new LoadedDiceFactory(6, 6));
    DiceResult result = diceSet.roll();

    Assertions.assertEquals(12, result.getValue());
    Assertions.assertTrue(result.areAllTheSame());

  }

  @Test
  void checkDouble3() {

    DiceSet diceSet = new DiceSet(6, 2, new LoadedDiceFactory(3, 3));
    DiceResult result = diceSet.roll();

    Assertions.assertEquals(6, result.getValue());
    Assertions.assertTrue(result.areAllTheSame());

  }

  @Test
  void check5and4() {

    DiceSet diceSet = new DiceSet(6, 2, new LoadedDiceFactory(5, 4));
    DiceResult result = diceSet.roll();

    Assertions.assertEquals(9, result.getValue());
    Assertions.assertFalse(result.areAllTheSame());

  }

  @Test
  void check1and6() {

    DiceSet diceSet = new DiceSet(6, 2, new LoadedDiceFactory(1, 6));
    DiceResult result = diceSet.roll();

    Assertions.assertEquals(7, result.getValue());
    Assertions.assertFalse(result.areAllTheSame());

  }

  @Test
  void checkTriple5() {

    DiceSet diceSet = new DiceSet(6, 3, new LoadedDiceFactory(5, 5, 5));
    DiceResult result = diceSet.roll();

    Assertions.assertEquals(15, result.getValue());
    Assertions.assertTrue(result.areAllTheSame());

  }

  @Test
  void checkFiveOnes() {

    DiceSet diceSet = new DiceSet(6, 5, new LoadedDiceFactory(1, 1, 1, 1, 1));
    DiceResult result = diceSet.roll();

    Assertions.assertEquals(5, result.getValue());
    Assertions.assertTrue(result.areAllTheSame());

  }

  @Test
  void checkFiveOnesAndATwoReturnNotAllTheSame() {

    DiceSet diceSet = new DiceSet(6, 6, new LoadedDiceFactory(1, 1, 1, 2, 1, 1));
    DiceResult result = diceSet.roll();

    Assertions.assertEquals(7, result.getValue());
    Assertions.assertFalse(result.areAllTheSame());

  }

  @Test
  void checkASetOfOneReturnsNotAllTheSame() {

    DiceSet diceSet = new DiceSet(6, 1, new LoadedDiceFactory(6));
    DiceResult result = diceSet.roll();

    Assertions.assertEquals(6, result.getValue());
    Assertions.assertFalse(result.areAllTheSame());

  }

  @Test
  void rollPair() {

    Integer max = 0, min = 100;

    for (int i = 0; i < 1000; i++) {
      Integer roll = new DiceSet(6, 2, new RandomDiceFactory()).roll().getValue();
      if (roll < min) {
        min = roll;
      }
      if (roll > max) {
        max = roll;
      }
    }

    Assertions.assertEquals("2,12", "" + min + "," + max);

  }

  @Test
  void rollThreeDiceOf12() {

    //Need to roll a lot of times!!!

    Integer max = 0, min = 100;

    for (int i = 0; i < 10000; i++) {
      Integer roll = new DiceSet(12, 3, new RandomDiceFactory()).roll().getValue();
      if (roll < min) {
        min = roll;
      }
      if (roll > max) {
        max = roll;
      }
    }

    Assertions.assertEquals("3,36", "" + min + "," + max);

  }

}

