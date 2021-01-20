package com.cm6123.snl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

class PlayerList implements Iterable<Player> {

  /**
   * A List of Players.
   */
  private List<Player> players;
  /**
   * The index to the current player in the list.
   */
  private Integer currentPlayer;

  /**
   * Create a PlayerList.  Rotate through the colours.
   *
   * @param count    how many Players
   * @param theStart where should each Player start
   */
  PlayerList(final Integer count,
             final Position theStart) {

    players = new ArrayList<>();

    for (int colour = 0; colour < count; colour++) {
      players.add(new Player(Player.PlayerColour.values()[colour], theStart));
    }

    currentPlayer = 0;

  }

  /**
   * Create a PlayerList.  Rotate through the colours.
   *
   * @param count    how many Players
   * @param theStart where should each Player start
   * @param currentPlayer - This describes the starting player of the game. Useful for loading the game.
   * @param initialPositions - Denotes the starting positions of each player, useful when loading a game
   */
  PlayerList(final Integer count,
             final Position theStart,
             final Integer currentPlayer,
             final Integer[] initialPositions) {

    players = new ArrayList<>();

    for (int colour = 0; colour < count; colour++) {
      players.add(new Player(Player.PlayerColour.values()[colour], theStart));
    }
    for (int i = 0; i < initialPositions.length; i++){
      players.get(i).moveTo(new Position(initialPositions[i]));
    }

    // -1 Because of 0-based indexing
    this.setCurrentPlayer(currentPlayer-1);

  }


  /**
   * Return the PlayerList as a List for standard use in other areas.
   * @return the PlayerList as a List of Players
   */
  public List<Player> asList() {
    return players;
  }

  /**
   * Get the current player.
   * @return the current Player object.
   */
  public Player getCurrentPlayer() {
    return players.get(currentPlayer);
  }

  /**
   * Go to the next player.  Roll around at the end of the list.
   */
  public void next() {
    currentPlayer = (currentPlayer + 1) % players.size();
  }

  /**
   * Get the Player at the provided index.
   * @param index the index (0 based) of the player required
   * @return the player at the specified index
   */
  public Player get(final Integer index) {
    return players.get(index);
  }

  /**
   * Return the number of players in the list.
   * @return the number of players in the list.
   */
  public Integer size() {
    return players.size();
  }

  /**
   * Get an iterator object on the PlayerList.
   * @return an Iterator on the players.
   */
  public Iterator<Player> iterator() {
    return players.iterator();
  }

  /**
   * Return a stream on the PlayerList.
   * @return a stream of Players.
   */
  public Stream<Player> stream() {
    return players.stream();
  }

  /**
   * @return - The index of current player. Useful when saving the game
   */
  public int getCurrentPlayerIndex() {
    return currentPlayer;
  }


  /**
   * Manually changing the current player. Useful when loading a game.
   * @param currentPlayer - The player value that should be set
   */
  public void setCurrentPlayer(Integer currentPlayer) {
    this.currentPlayer = currentPlayer;
  }
}

