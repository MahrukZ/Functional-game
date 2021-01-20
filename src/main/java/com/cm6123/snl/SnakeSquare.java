package com.cm6123.snl;

public class SnakeSquare extends Square {

  /**
   * The tail of the snake.
   */
  private Square destination;

  /**
   * Create a SnakeSquare on the board, at a position, with a tail as the destination.
   *
   * @param aBoard - the board on which the Square is
   * @param index - the index of the square on which the player lands (i.e. the head of the snake)
   * @param tail - the tail of the snake where the player ends
   */
  public SnakeSquare(final Board aBoard,
                     final Integer index,
                     final Square tail) {
    super(aBoard, index);
    destination = tail;
  }

  /**
   * Get the destination.  That is the tail of the snake.
   * @return = where the player goes if they land on the head of the snake.
   */
  public Square destination() {
    return destination;
  }

}
