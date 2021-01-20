package com.cm6123.snl;

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;

public final class Game {

  /**
   * The board on which the game is being played.
   */
  private Board board;
  /**
   * The list of players playing the game.
   */
  private PlayerList players;


  Game(final Integer playerCount,
       final Integer width,
       final Integer[] snakes,
       final Integer[] ladders,
       final Integer[] boosts
  ) {

    board = new Board(width, snakes, ladders, boosts, false);
    players = new PlayerList(playerCount, board.start());

  }
  Game(final Integer playerCount,
       final Integer width,
       final Integer[] snakes,
       final Integer[] ladders,
       final Integer[] boosts,
       final Boolean isWinningOnly
  ) {

    board = new Board(width, snakes, ladders, boosts, isWinningOnly);
    players = new PlayerList(playerCount, board.start());

  }

  Game(final Integer playerCount,
       final Integer width,
       final Integer[] snakes,
       final Integer[] ladders,
       final Integer[] boosts,
       final Boolean isWinningOnly,
       final Integer currentPlayer,
       final Integer[] initialPositions
  ) throws IllegalStateException{

    board = new Board(width, snakes, ladders, boosts, isWinningOnly);
    players = new PlayerList(playerCount, board.start(), currentPlayer, initialPositions);
  }



  Integer numberOfSquares() {
    return board.size();
  }

  /**
   * Get the number of players in the game.
   *
   * @return the number of players in the game.
   */
  public Integer numberOfPlayers() {
    return players.size();
  }

  List<Player> getPlayers() {
    return players.asList();
  }

  /**
   * Move the current player by a given number of squares.
   * @param squares the number of squares to move by - typically the value of the roll of the dice.
   */
  public void moveCurrentPlayer(final Integer squares) {
    if (isGameOver()) {
      throw new IllegalStateException("Can't move a player once the game is over.");
    } else {
      /**
       * First projecting the final position of the player, because of boost squares
       * and then moving to that position.
       */
      Player currentPlayer = getCurrentPlayer();
      Position startPos = currentPlayer.getPosition();
      int start = startPos.get();
      int jump = squares;
      while (start+jump < board.size()-1 && board.isBoostPosition(new Position(start + jump))){
        jump += squares;
      }
      Position newPosition = board.move(startPos, jump);
      currentPlayer.moveTo(newPosition);

      if (gameContinues()) {
        players.next();
      }
    }
  }

  /**
   * Get a read-only object for the players.
   * @param index - which player
   * @return a PlayerData object containing read-only data about the Player.
   */
  public PlayerData getPlayerData(final Integer index) {
    return players.get(index).getPlayerData();
  }

  /**
   * Get a read-only object for the current player.
   * @return a PlayerData object containing read-only data about the current player.
   */
  public PlayerData getPlayerData() {
    return players.getCurrentPlayer().getPlayerData();
  }

  /**
   * Getter for the current player
   * @return - current player object
   */
  public Player getCurrentPlayer() {
    return players.getCurrentPlayer();
  }

  /**
   * Is the game over?
   * @return true if the game is over.
   */
  public Boolean isGameOver() {
    return getCurrentPlayer().getPosition().get() >= board.size() - 1;//board.isWinningPosition(getCurrentPlayer().getPosition());
  }

  /**
   * Get the winning player.
   * @return - Player data about the winning player
   */
  public PlayerData getWinningPlayer() {
    if (isGameOver()) {
      return getCurrentPlayer().getPlayerData();
    } else {
      throw new IllegalStateException("The Game isn't over.");
    }
  }

  /**
   * Get the board being played on.
   * MAYBE THIS SHOULD BE A READ-ONLY REPRESENTATION?
   * @return the board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * shoudl the game carry on?
   * @return true if the game is not over.
   */
  private Boolean gameContinues() {
    return !isGameOver();
  }

  /**
   * should the game player make another move?
   * @return true if the cuurent player is at a boost square.
   */
  private boolean onBoostSquare() {
    return board.isBoostPosition(getCurrentPlayer().getPosition());
  }

  /**
   * This function provides the required data for each player to store in database.
   * @return - A List of Triplets, index, position and colour of each player.
   */

  public List<Triplet<Integer, Integer, String>> getAllPlayerPositions() {
    int playerSize = numberOfPlayers();
    ArrayList<Triplet<Integer, Integer, String>> allPlayers = new ArrayList<>();
    for (int i = 0; i < playerSize; i++){
      Player p = players.get(i);
      int position = p.getPosition().get();
      String colour = p.getColour().toString();
      Triplet<Integer, Integer, String> q = new Triplet<>(i, position, colour);
      allPlayers.add(q);
    }
    return allPlayers;
  }

  /**
   * @return - The current player index. Useful when saving the game.
   */
  public int getCurrentPlayerIndex () {
    return players.getCurrentPlayerIndex();
  }

}
