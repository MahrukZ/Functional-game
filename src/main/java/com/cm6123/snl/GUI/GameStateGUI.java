package com.cm6123.snl.GUI;

import com.cm6123.snl.*;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateGUI extends JPanel {
    /**
     * Game object to hold the current game, that is being displayed on board
     */
    private Game currentGame;
    /**
     * Table for representing player positions
     */
    private JTable playerTable;
    /**
     * Table for representing snake positions
     */
    private JTable snakeTable;
    /**
     * Table for representing ladder positions
     */
    private JTable ladderTable;
    /**
     * List for positions of boost squares
     */
    private JList<Integer> boostSqList;
    /**
     * Integer denoting the space the panel takes, in terms of height
     */
    private int lowerBound;

    /**
     * Constructor to create the component with default game
     * @param game - provided game
     */

    GameStateGUI(Game game) {
        super(null);
        currentGame = game;
        initView();
    }

    /**
     * Getter to get the current game object.
     * @return - Current game object
     */
    public Game getCurrentGame() {
        return currentGame;
    }

    /**
     * A function that will initialize the game state on UI
     */
    private void initView(){
        String[] playerColumns = { "Player Colour", "Player Position"};
        String[] sqColumns = {"From", "To"};
        List<Triplet<Integer, Integer, String>> players =  currentGame.getAllPlayerPositions();
        String[][] data = new String[players.size()][2];
        int i = 0;
        for (Triplet<Integer, Integer, String> player: players){
            data[i++] = new String[]{player.getValue2(), player.getValue1()+1+""};
        }

        JLabel stateHeading = new JLabel("Game State");
        stateHeading.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        stateHeading.setBounds(10,0,150,25);
        add(stateHeading);

        JLabel playersHeading = new JLabel("Player positions");
        playersHeading.setBounds(10,25,150,25);
        add(playersHeading);

        playerTable = new JTable(data, playerColumns);
        playerTable.setBounds(10, 50, 150, players.size() * 16);
        add(playerTable);

        List<Square> squares = getCurrentGame().getBoard().getSquares();
        String[][] snakes = getSnakes(squares);
        String[][] ladders = getLadders(squares);
        Integer[] boosts = getBoosts(squares);

        if (snakes.length != 0) {
            JLabel snakesHeading = new JLabel("Snake Positions");
            snakesHeading.setBounds(10, 160, 150, 25);
            add(snakesHeading);

            snakeTable = new JTable(snakes, sqColumns);
            snakeTable.setBounds(10, 190, 150, snakes.length * 18);
            add(snakeTable);
        }
        lowerBound = 190 + snakes.length*18 + 20;

        if (ladders.length != 0) {
            JLabel laddersHeading = new JLabel("Ladder Positions");
            laddersHeading.setBounds(10, lowerBound, 150, 25);
            add(laddersHeading);
            ladderTable = new JTable(ladders, sqColumns);
            ladderTable.setBounds(10, lowerBound + 30, 150, ladders.length * 18);
            add(ladderTable);
            lowerBound += ladders.length * 18 + 50;
        }

        if (boosts.length != 0) {
            JLabel boostsHeading = new JLabel("Boost Squares");
            boostsHeading.setBounds(10, lowerBound, 150, 25);
            add(boostsHeading);
            boostSqList = new JList<>(boosts);
            boostSqList.setBounds(10, lowerBound + 30, 50, boosts.length * 18);
            boostSqList.setLayoutOrientation(JList.VERTICAL);
            add(boostSqList);
            lowerBound += boosts.length*18 + 50;
        }
        lowerBound = Math.max(lowerBound, 300);
        Application.setSize(400, lowerBound+110);
    }

    /**
     * Function to extract all the snake positions
     * @param squares - List of squares from the board
     * @return - 2d array represting the snake positions, ready to be placed in the table
     */
    private String[][] getSnakes(List<Square> squares) {
        List<Pair<Integer, Integer>> snakesList = new ArrayList<>();
        for (Square sq: squares){
            if (sq instanceof SnakeSquare){
                SnakeSquare snakeSquare = (SnakeSquare)sq;
                int a = snakeSquare.getNumber(), b = snakeSquare.destination().getNumber();
                snakesList.add(new Pair<>(a, b));
            }
        }
        String[][] snakes = new String[snakesList.size()][2];
        int i = 0;
        for (Pair<Integer, Integer> snake: snakesList){
            snakes[i++] = new String[]{snake.getValue0()+1+"", snake.getValue1()+1+""};
        }
        return snakes;
    }

    /**
     * Function to get the ladder positions from list of squares
     * @param squares - List of squares form board
     * @return - 2d array of strings representing ladder top and bottom, ready to be placed into the table.
     */
    private String[][] getLadders(List<Square> squares) {
        List<Pair<Integer, Integer>> laddersList = new ArrayList<>();
        for (Square sq: squares){
            if (sq instanceof LadderSquare){
                LadderSquare ladderSquare = (LadderSquare)sq;
                int a = ladderSquare.getNumber(), b = ladderSquare.destination().getNumber();
                laddersList.add(new Pair<>(a, b));
            }
        }
        String[][] ladders = new String[laddersList.size()][2];
        int i = 0;
        for (Pair<Integer, Integer> ladder: laddersList){
            ladders[i++] = new String[]{ladder.getValue0()+1+"", ladder.getValue1()+1+""};
        }
        return ladders;
    }

    /**
     * Get the boost square positions
     * @param squares - List of squares from the board.
     * @return - List of integers denoting positions of boost squares
     */
    private Integer[] getBoosts(List<Square> squares) {
        List<Integer> boostList = new ArrayList<>();
        for (Square sq: squares){
            if (sq.isBoostSquare()){
                boostList.add(sq.getNumber()+1);
            }
        }
        return boostList.toArray(new Integer[0]);
    }

    /**
     * Get lowerBound of the panel
     */
    public int getLowerBound(){
        return this.lowerBound;
    }
    /**
     * Move the current player of current game.
     * @param dice - moves/steps
     * @return - The next player's colour
     * @throws IllegalStateException - If the game is already over
     */
    public String moveCurrentPlayer(int dice) throws IllegalStateException{
        if (currentGame.isGameOver()){
            throw new IllegalStateException("Can't move a player once the game is over.");
        }
        currentGame.moveCurrentPlayer(dice);
        changePositions(currentGame.getAllPlayerPositions());
        return currentGame.getCurrentPlayer().getColour().toString();
    }

    /**
     * Updates player positions after each dice roll
     * @param positions, A triplet denoting the player positions
     */
    private void changePositions(List<Triplet<Integer, Integer, String>> positions) {
        // updating positions of all players
        TableModel tm = playerTable.getModel();
        for (int i = 0; i < positions.size(); i++) {
            tm.setValueAt(positions.get(i).getValue2(), i, 0);
            tm.setValueAt(positions.get(i).getValue1()+1+"", i, 1);
        }
    }
}
