package com.cm6123.snl;

import java.util.Objects;

public final class Position {

  /**
   * The position knows it's own index on the board.
   */
  private Integer value;

  /**
   * Create a Position object that represents
   * a read-only representation of where the player is.
   *
   * @param aValue the number of the position
   */
  public Position(final Integer aValue) {
    this.value = aValue;
  }

  /**
   * Get the number of the position.
   * @return the number of the Position
   */
  public Integer get() {
    return value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position position = (Position) o;
    return Objects.equals(value, position.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "Position{"
            + "value="
            + value
            + '}';
  }
}
