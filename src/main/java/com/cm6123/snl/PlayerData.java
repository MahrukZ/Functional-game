package com.cm6123.snl;

import java.util.Objects;

public final class PlayerData {

  /**
   * The colour of the Player's token.
   */
  private final Player.PlayerColour colour;
  /**
   * The position of the player.
   */
  private final Position position;

  /**
   * Create a read-only representation of a Player.
   *
   * @param aColour the colour
   * @param pos     the position of the player
   */
  public PlayerData(final Player.PlayerColour aColour, final Position pos) {
    colour = aColour;
    position = pos;
  }

  /**
   * get the colour of the Player.
   *
   * @return the colour of the Player's token.
   */
  public Player.PlayerColour getColour() {
    return colour;
  }

  /**
   * Get the position of the Player.
   *
   * @return the position of the Player.
   */
  public Position getPosition() {
    return position;
  }

  /**
   * A string representation of the Player.
   *
   * @return the String representation.
   */
  public String toString() {
    return "Player: " + colour + " is at position :" + position.get();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlayerData that = (PlayerData) o;
    return colour == that.colour
            &&
            Objects.equals(position, that.position);
  }

  @Override
  public int hashCode() {
    return Objects.hash(colour, position);
  }
}
