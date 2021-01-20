package com.cm6123.snl.GUI;

import com.cm6123.snl.Application;
import com.cm6123.snl.Database.DBManager;
import com.cm6123.snl.Game;
import com.cm6123.snl.GameBuilder;
import com.cm6123.snl.dice.Dice;
import com.cm6123.snl.dice.RandomDice;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class StartMenuGUI extends JPanel{

    /**
     * Button to let the user create a new game
     */
    private JButton btnCreate = new JButton("Create A New Game");

    /**
     * Button to let the user load the game
     */
    private JButton btnLoad = new JButton("Load Previous Game");

    /**
     * Button to exit the game
     */
    private JButton btnExit = new JButton("Exit Game");

    /**
     * This button will start the new game after getting required information about the game.
     */
    private JButton startButton = new JButton("Start the game");

    /**
     * This button will take the user back to start screen
     */
    private JButton backButton = new JButton("Back to menu");
    /**
     * Save game button that will added in the sideBar.
     */
    private JButton btnSaveGame = new JButton("Save The Current Game");

    /**
     * Spinner model to limit board size.
     */
    SpinnerNumberModel boardSpinnerModel = new SpinnerNumberModel(5, 5, 10, 1);
    /**
     * Input field where user will enter the board size.
     */
    JSpinner boardSizeInput = new JSpinner(boardSpinnerModel);
    /**
     * Spinner model to limit player numbers.
     */
    SpinnerNumberModel playerSpinnerModel = new SpinnerNumberModel(1, 1, 5, 1);
    /**
     * Input Field where user will enter number of players.
     */
    JSpinner numberPlayerInput = new JSpinner(playerSpinnerModel);
    /**
     * Input Field where user will input all the boost square positions, space separated.
     */
    JTextField boostSquaresInput = new JTextField(20);
    /**
     * Input Field where user will input all the snake positions, space separated.
     */
    JTextField snakeSquaresInput = new JTextField(20);
    /**
     * Input Field where user will input all the ladder positions, space separated.
     */
    JTextField ladderSquaresInput = new JTextField(20);
    /**
     * Label to tell the user to input board size.
     */
    JLabel boardSizeLabel = new JLabel("Board Size");
    /**
     * Label to tell the user to input number of players.
     */
    JLabel numberPlayersLabel = new JLabel("No of players");
    /**
     * Label to tell the user to input boost square positions.
     */
    JLabel boostSquaresLabel = new JLabel("Boost Squares");
    /**
     * Label to tell the user to input snake positions.
     */
    JLabel snakeSquaresLabel = new JLabel("Snake Squares");
    /**
     * Label to tell the user to input ladder positions.
     */
    JLabel ladderSquaresLabel = new JLabel("Ladder Squares");
    /**
     * This layout will be helpful to organize components on the screen.
     */
    private BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
    /**
     * This dynamic list would be bind to the listView.
     */
    private Vector<String> gameIDs = new Vector<>();
    /**
     * A listView that will have all the games previously stored in the database
     */
    private JList<String> gameList = new JList<>(gameIDs);
    /**
     * Label to denote the gameIDs in loading screen.
     */
    private JLabel heading = new JLabel("Game ID");
    /**
     * Side bar with options during the game, like rolling a dice, and saving the game
     */
    private JPanel sideBar;
    /**
     * Label to display the current player with colour.
     */
    private JLabel currentPlayerColour = new JLabel("");
    /**
     * Button to let the user roll the dice.
     */
    private JButton rollDice = new JButton("Roll the Dice!");
    /**
     * Label that would display the current dice value.
     */
    private JLabel diceValue = new JLabel("Roll the dice to continue");
    /**
     * This object will represent the game state.
     */
    private GameStateGUI gameStateGUI;
    /**
     * Dice object to deal will rolling.
     */
    private Dice dice = new RandomDice(6);
    /**
     * GameID to denoting the current game id in the database
     */
    private Integer gameID = null;




    public StartMenuGUI() {
        super(null);
        setSize(500, 500);
        // Add components of main screen
        addMainMenu();
        loadMainScreen();

        addCreateForm();
        hideCreateForm();

        addLoadFormComponents();
        hideLoadForm();

        add(backButton);
        backButton.setVisible(false);

        // Adding all listeners
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startLoadForm();
            }
        });
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCreateForm();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        rollDice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backButton.setVisible(false);
                hideCreateForm();
                hideLoadForm();
                loadMainScreen();
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Game newGame = createGame();
                    backButton.setVisible(false);
                    startGame(newGame);
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });
        gameList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                gameListHandler(arg0);
            }
        });
    }

    /**
     * Add the main screen components
     */
    private void addMainMenu(){
        btnCreate.setBounds(75, 100, 200, 30);
        add(btnCreate);
        btnLoad.setBounds(75, 150, 200, 30);
        add(btnLoad);
        btnExit.setBounds(125, 200, 100, 30);
        add(btnExit);
    }
    /**
     * Load the main screen
     */
    private void loadMainScreen() {
        //setLayout(boxLayout);
        btnCreate.setVisible(true);
        btnLoad.setVisible(true);
        btnExit.setVisible(true);
    }

    /**
     * Add all the components of createForm
     */
    private void addCreateForm(){

        boardSizeLabel.setBounds(10, 5, 100, 25);
        boardSizeInput.setBounds(150, 5, 100, 25);
        numberPlayersLabel.setBounds(10, 40, 100, 25);
        numberPlayerInput.setBounds(150, 40, 100, 25);
        boostSquaresLabel.setBounds(10, 75, 100, 25);
        boostSquaresInput.setBounds(150, 75, 100, 25);
        snakeSquaresLabel.setBounds(10, 110, 100, 25);
        snakeSquaresInput.setBounds(150, 110, 100, 25);
        ladderSquaresLabel.setBounds(10, 145, 100, 25);
        ladderSquaresInput.setBounds(150, 145, 100, 25);
        startButton.setBounds(75, 200, 150, 35);

        add(boardSizeLabel);
        add(boardSizeInput);
        add(numberPlayersLabel);
        add(numberPlayerInput);
        add(boostSquaresLabel);
        add(boostSquaresInput);
        add(snakeSquaresLabel);
        add(snakeSquaresInput);
        add(ladderSquaresLabel);
        add(ladderSquaresInput);
        add(startButton);
    }
    /**
     * Display the create game form
     */
    private void startCreateForm() {
        btnLoad.setVisible(false);
        btnCreate.setVisible(false);
        btnExit.setVisible(false);
        //setLayout(new FlowLayout());
        boardSizeLabel.setVisible(true);
        boardSizeInput.setVisible(true);
        numberPlayersLabel.setVisible(true);
        numberPlayerInput.setVisible(true);
        boostSquaresLabel.setVisible(true);
        boostSquaresInput.setVisible(true);
        snakeSquaresLabel.setVisible(true);
        snakeSquaresInput.setVisible(true);
        ladderSquaresLabel.setVisible(true);
        ladderSquaresInput.setVisible(true);
        startButton.setVisible(true);

        backButton.setBounds(75, 250, 150, 35);
        backButton.setVisible(true);
    }
    /**
     * Add all the loadGame components
     */
    private void addLoadFormComponents(){
        add(gameList);
        heading.setBounds(130, 10, 50, 35);
        add(heading, Component.CENTER_ALIGNMENT);
    }
    /**
     * Display the load game form
     */
    private void startLoadForm() {
        List<String> gameIDsNew = new ArrayList<>();
        try {
            DBManager dbManager = new DBManager();
            gameIDsNew = dbManager.getAllGames();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error connecting to database");
        }

        gameIDs.clear();
        gameIDs.addAll(gameIDsNew);
        gameList.setListData(gameIDs);
        btnCreate.setVisible(false);
        btnLoad.setVisible(false);
        btnExit.setVisible(false);

        int height = gameIDs.size() * 18;
        gameList.setBounds(140, 40, 120, height);
        gameList.setVisible(true);
        heading.setVisible(true);

        backButton.setBounds(100, 60+height, 150, 35);
        backButton.setVisible(true);

        Application.setSize(375, Math.max(400, height+200));
    }
    /**
     * Hide loadGame components
     */
    private void hideLoadForm(){
        gameList.setVisible(false);
        heading.setVisible(false);
    }

    /**
     * Hide all the elements of create game form
     */
    private void hideCreateForm(){
        // Hide all the labels and input now!!
        numberPlayersLabel.setVisible(false);
        numberPlayerInput.setVisible(false);
        boostSquaresInput.setVisible(false);
        boostSquaresLabel.setVisible(false);
        boardSizeInput.setVisible(false);
        boardSizeLabel.setVisible(false);
        boostSquaresLabel.setVisible(false);
        numberPlayerInput.setVisible(false);
        snakeSquaresInput.setVisible(false);
        snakeSquaresLabel.setVisible(false);
        ladderSquaresLabel.setVisible(false);
        ladderSquaresInput.setVisible(false);
        startButton.setVisible(false);
    }

    /**
     * Create game, from the details that user entered
     * @return - new Game object represting the game.
     */
    private Game createGame() throws Exception{
        Game newGame;
        int boardSize = Integer.parseInt(boardSizeInput.getValue().toString());
        int numberPlayers = Integer.parseInt(numberPlayerInput.getValue().toString());
        String boostString = boostSquaresInput.getText();
        String[] boosts = boostString.equals("") ? new String[0] : boostString.strip().split("\\s+");
        Integer boostsInt[] = new Integer[boosts.length];
        for (int i = 0; i < boosts.length; i++) {
            boostsInt[i] = Integer.parseInt(boosts[i].strip()) - 1;
        }
        String snakeString = snakeSquaresInput.getText();
        String[] snakes = snakeString.equals("") ? new String[0] : snakeString.strip().split("\\s+");
        Integer snakesInt[] = new Integer[snakes.length];
        for (int i = 0; i < snakes.length; i++) {
            snakesInt[i] = Integer.parseInt(snakes[i].strip()) - 1;
        }
        String ladderString = ladderSquaresInput.getText();
        String[] ladders = ladderString.equals("") ? new String[0] : ladderString.strip().split("\\s+");
        Integer laddersInt[] = new Integer[ladders.length];
        for (int i = 0; i < ladders.length; i++) {
            laddersInt[i] = Integer.parseInt(ladders[i].strip()) - 1;
        }
        newGame = new GameBuilder()
                .withBoardSize(boardSize)
                .withPlayers(numberPlayers)
                .withBoosts(boostsInt)
                .withSnakes(snakesInt)
                .withLadders(laddersInt)
                .withIsWinningEnabled()
                .build();
        hideCreateForm();
        return newGame;
    }


    /**
     * Detects the game that user selected to load
     * @param arg0 - The event that ahs selected game index
     */
    private void gameListHandler(ListSelectionEvent arg0) {
        if (!arg0.getValueIsAdjusting()) {
            gameID = Integer.parseInt(gameList.getSelectedValue().split("-")[0].strip());
            try {
                Game currentGame = new DBManager().loadGame(gameID);
                heading.setVisible(false);
                gameList.setVisible(false);
                backButton.setVisible(false);
                startGame(currentGame);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    /**
     * And finally the funtion that starts the game.
     * @param currentGame - The game that is to be started. Can be loaded or created.
     */
    private void startGame(Game currentGame) {
        hideCreateForm();
        gameStateGUI = new GameStateGUI(currentGame);
        //setLayout(new BorderLayout());
        btnSaveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DBManager dbManager = new DBManager();
                    if (gameID != null){
                        dbManager.updateGame(gameID, gameStateGUI.getCurrentGame());
                    }else {
                        gameID = dbManager.saveGame(gameStateGUI.getCurrentGame());
                    }
                    JOptionPane.showMessageDialog(null, "Game saved successfully");
                }catch (Exception e3){
                    JOptionPane.showMessageDialog(null, e3.getMessage());
                }
            }
        });
        setSize(375, 400);
        sideBar = new JPanel(null);
        currentPlayerColour = new JLabel("Current Player: " + currentGame.getCurrentPlayer().getColour().toString());
        currentPlayerColour.setBounds(10, 10, 150, 40);
        sideBar.add(currentPlayerColour);
        diceValue.setBounds(10, 60, 150, 40);
        sideBar.add(diceValue);


        int lastPoint = gameStateGUI.getLowerBound();
        sideBar.setBounds(180, 0, 200, 300);
        gameStateGUI.setBounds(0,0,180, lastPoint);
        add(sideBar);
        add(gameStateGUI);

        rollDice.setBounds(10, lastPoint+20, 150, 40);
        btnSaveGame.setBounds(175, lastPoint+20, 175, 40);
        add(rollDice);
        add(btnSaveGame);
        gameList.setVisible(false);
    }

    /**
     * The function that is called after rolling the dice
     */
    private void rollDice() {
        int val = dice.roll();
        diceValue.setText(""+val);
        String nextPlayer = "";
        try {
            nextPlayer = gameStateGUI.moveCurrentPlayer(val);
        }catch (IllegalStateException e2){
            JOptionPane.showMessageDialog(null, e2.getMessage());
        }
        if (gameStateGUI.getCurrentGame().isGameOver()) {
            currentPlayerColour.setText("Winner: " + gameStateGUI.getCurrentGame().getCurrentPlayer().getColour());
        }else{
            currentPlayerColour.setText("Current Player: " + nextPlayer);
        }
        repaint();
    }

}
