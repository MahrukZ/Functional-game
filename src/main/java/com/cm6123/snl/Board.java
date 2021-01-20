package com.cm6123.snl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Board {
  /**
   * width of the board.
   */
  private final Integer width;
  /**
   * Flag to see if the 2nd option is enabled
   */
  private final Boolean isWinningOnly;
  /**
   * internal set of Squares representing the board.
   */
  private final List<Square> squares;
  /**
   * Internal set of special (snake or ladder) squares.
   */
  private final Set<Integer> specials;

  /**
   * @param aWidth - the width of the board (remember it is square)
   * @param snakes - the snakes in pairs (head, tail, head, tail)
   * @param ladders - the ladders in pairs (foot, top, foot, top)
   * @param boosts - the boosts
   * @param isWinningOnly - the flag that decides that Q2 is enabled or not
   */
  public Board(
          final Integer aWidth,
          final Integer[] snakes,
          final Integer[] ladders,
          final Integer[] boosts,
          final Boolean isWinningOnly) {

    this.width = aWidth;
    squares = new ArrayList<>();
    for (int w = 0; w < aWidth; w++) {
      for (int h = 0; h < aWidth; h++) {
        squares.add(new Square(this, (w * aWidth) + h));
      }
    }

    setLastSquareAsWinner();
    specials = new HashSet<>();
    this.isWinningOnly = isWinningOnly;
    addSnakesAndLaddersAndBoosts(snakes, ladders, boosts);

  }
  /**
   * @param aWidth - the width of the board (remember it is square)
   * @param snakes - the snakes in pairs (head, tail, head, tail)
   * @param ladders - the ladders in pairs (foot, top, foot, top)
   */
  public Board(
          final Integer aWidth,
          final Integer[] snakes,
          final Integer[] ladders) {

    this(aWidth, snakes, ladders, new Integer[0], false);
  }
  /**
   * @param aWidth - the width of the board (remember it is square)
   * @param snakes - the snakes in pairs (head, tail, head, tail)
   * @param ladders - the ladders in pairs (foot, top, foot, top)
   * @param boosts - the boosts
   */
  public Board(
          final Integer aWidth,
          final Integer[] snakes,
          final Integer[] ladders,
          final Integer[] boosts) {
    this(aWidth, snakes, ladders, boosts, false);
  }

  /**
   * Create a board with the given width.  It will be a square board.
   *
   * @param aWidth - the width of the board
   */
  public Board(final Integer aWidth) {

    this(aWidth, new Integer[0], new Integer[0], new Integer[0], false);


  }

  /**
   * @return the size of the board = the number of squares.
   */
  public Integer size() {
    return squares.size();
  }

  /**
   * @return returns the starting square.
   */
  public Position start() {
    return new Position(squares.get(0).getNumber());
  }

  /**
   * @return return the width of the board
   */
  public Integer getWidth() {
    return width;
  }

  /**
   * @param from the starting position.
   * @param roll the number of squares to move (i.e. the dice roll).
   * @return the new position after moving.
   */
  Position move(final Position from, final Integer roll) {
    Integer tempMove = from.get() + roll;

    if (tempMove < squares.size()-1) {
      return new Position(squares.get(tempMove).destination().getNumber());
    }else if(tempMove == squares.size()-1) {
      return new Position((squares.get(size() - 1)).getNumber());
    }else{
      if (isWinningOnly)
        return from;
      else
        return new Position((squares.get(size() - 1)).getNumber());
    }
  }

  /**
   * set the last square on the board to be the winning square.
   * Landing on this square means that the player has won.
   */
  private void setLastSquareAsWinner() {
    int lastSquare = this.width * this.width;
    squares.get(lastSquare - 1).setAsWinningSquare();
  }

  /**
   * Returns true if the passed position is a winning position.
   *
   * @param aPosition - a position of a player
   * @return true if the passed position is a winning position.
   */
  public boolean isWinningPosition(final Position aPosition) {
    return squares.get(aPosition.get()).isWinningSquare();
  }

  /**
   * @param aPosition - a position of a player
   * @return true if the passed position is a winning position.
   */
  public boolean isBoostPosition(final Position aPosition) { return squares.get(aPosition.get()).isBoostSquare(); }

  private void addSnakesAndLaddersAndBoosts(
          final Integer[] snakes,
          final Integer[] ladders,
          final Integer[] boosts) {
    addSnakes(snakes);
    addLadders(ladders);
    addBoosts(boosts);
  }

  private void addLadders(final Integer[] ladders) {
    for (Integer ladder = 0; ladder <= ladders.length - 1; ladder += 2) {
      this.addLadder(ladders[ladder], ladders[ladder + 1]);
    }
  }

  private void addSnakes(final Integer[] snakes) {
    for (Integer snake = 0; snake <= snakes.length - 1; snake += 2) {
      this.addSnake(snakes[snake], snakes[snake + 1]);
    }
  }

  private void addBoosts(final Integer[] boosts) {
    for (int boost: boosts) {
      this.addBoost(boost);
    }
  }


  private void addSnake(final Integer head, final Integer tail) {
    if (specials.contains(head) || specials.contains(tail)) {
      throw new IllegalStateException("Snakes and Ladders can't clash");
    }

    if (head == tail) {
      throw new IllegalStateException("Snake can't go to itself");
    }

    if (head < tail) {
      throw new IllegalStateException("Snake can't go up");

    }

    specials.add(head);
    specials.add(tail);

    Square tailSquare = squares.get(tail);
    SnakeSquare snake = new SnakeSquare(this, head, tailSquare);
    this.setSquareAt(head, snake);

  }

  private void addLadder(final Integer foot, final Integer top) {
    if (specials.contains(foot) || specials.contains(top)) {
      throw new IllegalStateException("Snakes and Ladders can't clash");
    }

    if (foot == top) {
      throw new IllegalStateException("Ladder can't go to itself");
    }

    if (foot > top) {
      throw new IllegalStateException("Ladder can't go down");
    }


    specials.add(foot);
    specials.add(top);
    Square topSquare = squares.get(top);
    Square ladder = new LadderSquare(this, foot, topSquare);
    this.setSquareAt(foot, ladder);

  }

  private void addBoost(final Integer boost) {
    if (specials.contains(boost)) {
      throw new IllegalStateException("Boosts can't clash with Snakes and Ladders");
    }
    specials.add(boost);
    Square currentSquare = squares.get(boost);
    currentSquare.setAsBoostSquare();
  }

  private void setSquareAt(final Integer index, final Square newSquare) {
    squares.set(index, newSquare);
  }

  /**
   * @return - Gets the square list which will be useful for adding sqaures to database
   */
  public List<Square> getSquares () {
    return this.squares;
  }
}
