package com.cm6123.snl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {

  @Test
  void size() {
    Board aBoard = new Board(5);
    Assertions.assertEquals(25, aBoard.size());
  }

  @Test
  void start() {
    Board aBoard = new Board(5);
    Assertions.assertEquals(0, aBoard.start().get());
  }

  @Test
  void move() {
    Board aBoard = new Board(5);
    Position start = aBoard.start();
    Position five = aBoard.move(start, 4);
    Assertions.assertEquals(4, five.get());
  }

  @Test
  void snakes_and_ladders_do_not_clash1() throws Exception {

    Assertions.assertThrows(IllegalStateException.class,
            () -> {
              Board aBoard = new Board(5, new Integer[]{3, 5}, new Integer[]{5, 9});
            });
  }

  @Test
  void snakes_and_ladders_do_not_clash2() throws Exception {

    Assertions.assertThrows(IllegalStateException.class,
            () -> {
              Board aBoard = new Board(5, new Integer[]{5, 2}, new Integer[]{5, 8});
            });
  }

  @Test
  void snakes_and_ladders_do_not_clash3() throws Exception {

    Assertions.assertThrows(IllegalStateException.class,
            () -> {
              Board aBoard = new Board(5, new Integer[0], new Integer[]{5, 8, 8, 12});
            });
  }


}
