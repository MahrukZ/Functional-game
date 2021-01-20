package com.cm6123.snl;

public class LadderSquare extends Square {

  /**
   * The top of the ladder.
   */
  private Square destination;

  /**
   * Create a ladder square that take you to the top when landed on.
   *
   * @param aBoard the board of which the Square is a part
   * @param index the number of the Square - and the bottom of the ladder
   * @param top the top of the ladder
   */
  public LadderSquare(final Board aBoard,
                      final Integer index,
                      final Square top) {
    super(aBoard, index);
    destination = top;
  }


  /**
   * get the top of the ladder.
   * @return the Square at the top of the ladder.
   */
  public Square destination() {
    return destination;
  }

}
