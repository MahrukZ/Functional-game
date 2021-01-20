package com.cm6123.snl;

public final class Player {

  /**
   * The colour of the player's token.
   */
  private final PlayerColour colour;
  /**
   * The current position of the player.
   */
  private Position position;

  /**
   * Make a player with a colour and place them at a start position.
   *
   * @param aColour the colour of the player's counter
   * @param start the starting position of the player
   */
  public Player(final PlayerColour aColour, final Position start) {
    colour = aColour;
    position = start;
  }

  /**
   * Move the player to a new position.
   * @param newPosition the position the player should move to
   */
  public void moveTo(final Position newPosition) {

    position = newPosition;
  }

  /**
   * Get the colour of the player's token.
   * @return the colour of the Player's token.
   */
  public PlayerColour getColour() {
    return colour;
  }

  /**
   * Get the current position of the player's token.
   * @return the current position of the player.
   */
  public Position getPosition() {
    return position;
  }


  /**
   * Get the String representation of the object.
   * @return the Player as a String.
   */
  public String toString() {
    return "Player: " + colour + " is at position :" + position.get();
  }

  /**
   * Get a read-only representation of the Player.
   * @return read-only version of the Player.
   */
  public PlayerData getPlayerData() {
    return new PlayerData(this.colour, this.position);
  }

  /**
   * The possible colours of the Player token.
   */
  public enum PlayerColour {
    /**
     *
     */
    RED,
    /**
     *
     */
    BLUE,
    /**
     *
     */
    YELLOW,
    /**
     *
     */
    GREEN,
    /**
     *
     */
    WHITE
  }


}
