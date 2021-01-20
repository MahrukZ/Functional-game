package com.cm6123.snl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InitialisePlayer {

  private Board board;

  @BeforeEach
  public void make_a_board() throws Exception {
    board = new Board(4);
  }

  @Test
  public void create_a_player_with_colour() throws Exception {

    Player gavin = new Player(Player.PlayerColour.RED, board.start());
    Assertions.assertEquals(Player.PlayerColour.RED, gavin.getColour());
    Assertions.assertEquals(0, gavin.getPosition().get());

  }

}
