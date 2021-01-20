package com.cm6123.snl;

public class Square {
  /**
   * The board on which the Square is placed.
   * CAN THIS BE DELETED?
   */
  private Board board;
  /**
   * The number of the square.
   */
  private Integer number;
  /**
   * Is this the winning square?
   */
  private Boolean isWinningSquare;

  /**
   * Is this the boost square?
   */
  private Boolean isBoostSquare;
  /**
   * Construct a Square.
   *
   * @param aBoard - the board of the which the square is part
   * @param num - the number of the square
   */
  public Square(final Board aBoard, final Integer num) {
    board = aBoard;
    number = num;
    isWinningSquare = false;
    isBoostSquare = false;
  }

  /**
   * Set this square as a winning square.
   */
  public void setAsWinningSquare() {
    isWinningSquare = true;
  }

  /**
   * Sets the boost flag of this  square.
   */
  public void setAsBoostSquare() { isBoostSquare = true; }
  /**
   * Get the number of the square.
   * @return the number of the square
   */
  public Integer getNumber() {
    return number;
  }

  /**
   * Get the destination of the Player should they land on this square.
   * @return the destination square.
   */
  protected Square destination() {
    return this;
  }

  /**
   * Return a string representation of the number of the Square.
   * Given that Java is 0 based, the label will be the number + 1;
   * Feels like there is a better solution.
   * @return the label of the Square.
   */
  public String getLabel() {
    return (getNumber() + 1) + "";
  }

  /**
   * True if this is a winning square.
   * @return true if this is a winning square.
   */
  public Boolean isWinningSquare() {
    return isWinningSquare;
  }

  /**
   * True if this is a boost square.
   * @return true if this is a boost square.
   */
  public Boolean isBoostSquare() { return isBoostSquare; }

}
