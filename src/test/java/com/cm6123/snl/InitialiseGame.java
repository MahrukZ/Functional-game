package com.cm6123.snl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class InitialiseGame {

  @Test
  public void can_create_a_default_game() throws Exception {

    Game aGame =
            new GameBuilder()
                    .build();
    Assertions.assertEquals(2, aGame.numberOfPlayers());
    Assertions.assertEquals(100, aGame.numberOfSquares());
    Assertions.assertEquals(aGame.getPlayers().get(0), aGame.getCurrentPlayer());

  }

  @Test
  public void can_set_number_of_players() throws Exception {
    Game aGame = new GameBuilder()
            .withPlayers(4)
            .build();
    Assertions.assertEquals(4, aGame.numberOfPlayers());
    Assertions.assertEquals(100, aGame.numberOfSquares());
  }


  @Test
  public void can_set_number_of_players_and_board_size() throws Exception {
    Game aGame = new GameBuilder()
            .withPlayers(4)
            .withBoardSize(4)
            .build();
    Assertions.assertEquals(4, aGame.numberOfPlayers());
    //board is always square
    Assertions.assertEquals(16, aGame.numberOfSquares());
  }

  @Test
  public void can_set_board_size_only() throws Exception {
    Game aGame = new GameBuilder()
            .withBoardSize(5)
            .build();
    Assertions.assertEquals(2, aGame.numberOfPlayers());
    //board is always square
    Assertions.assertEquals(25, aGame.numberOfSquares());
  }

  @Test
  public void can_set_number_of_players_and_board_size_in_reverse_order() throws Exception {
    Game aGame = new GameBuilder()
            .withBoardSize(5)
            .withPlayers(3)
            .build();
    Assertions.assertEquals(3, aGame.numberOfPlayers());
    //board is always square
    Assertions.assertEquals(25, aGame.numberOfSquares());
  }

  @Test
  public void all_players_should_be_on_square_one_at_start_of_game() throws Exception {
    Game aGame = new GameBuilder()
            .build(); //default first

    //new assertions
    //need to get the players (represented as tokens?) and check their positions.

    for (Player aPlayer : aGame.getPlayers()) {
      Assertions.assertEquals(0, aPlayer.getPosition().get());
    }

  }

  @Test
  public void board_must_be_at_least_four_by_four() throws Exception {

    Assertions.assertThrows(IllegalArgumentException.class,
            () -> {

              Game aGame = new GameBuilder()
                      .withBoardSize(3)
                      .build();
            }
    );
  }


  @Test
  public void game_must_have_at_least_one_player() throws Exception {

    Assertions.assertThrows(IllegalArgumentException.class,
            () -> {

              Game aGame = new GameBuilder()
                      .withPlayers(0)
                      .build();
            }
    );
  }

  @Test
  public void game_can_have_a_maximum_of_five_players() throws Exception {

    Assertions.assertThrows(IllegalArgumentException.class,
            () -> {

              Game aGame = new GameBuilder()
                      .withPlayers(6)
                      .build();
            }
    );
  }

  @Test
  public void game_must_have_different_colours() throws Exception {

    Game aGame = new GameBuilder()
            .withPlayers(2)
            .withBoardSize(4)
            .build();

    Set<Player.PlayerColour> coloursInGame = new HashSet<>();
    coloursInGame = aGame.getPlayers()
            .stream()
            .map(Player::getColour)
            .collect(Collectors.toSet());

    Assertions.assertEquals(2, coloursInGame.size());

  }


}
