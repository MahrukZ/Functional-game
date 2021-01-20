package com.cm6123.snl.Database;

import com.cm6123.snl.*;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBManager {
    private Connection con;

    /**
     * Constructor to define the manager object
     */
    public DBManager() throws SQLException{
        con = DBUtil.connectToServer();
        ensureDatabaseCreated(DBUtil.getDBName());
        ensureTablesCreated();
    }

    /**
     * Creates database if not already there
     * @param dbName - The name of the database
     */
    private void ensureDatabaseCreated(String dbName) throws SQLException{
        Statement st = null;

        st = con.createStatement();
        st.execute("CREATE DATABASE IF NOT EXISTS " + dbName);
        st.execute("USE " + dbName);
        st.close();
    }


    /**
     * creates all the required tables if not already there
     */
    private void ensureTablesCreated() throws SQLException{
        Statement st = null;
        String createQuery[] = {"\n" +
                "CREATE TABLE IF NOT EXISTS Boards(\n" +
                "\tBoardID INT NOT NULL AUTO_INCREMENT,\n" +
                "\tSize INT NOT NULL,\n" +
                "\tPRIMARY KEY (BoardID)\n" +
                ");\n" +
                "\n",
                "CREATE TABLE IF NOT EXISTS Games(\n" +
                        "\tGameID INT NOT NULL AUTO_INCREMENT,\n" +
                        "\tBoardID INT NOT NULL,\n" +
                        "\tIsGameOver BOOLEAN,\n" +
                        "\tCurrentPlayer INT,\n" +
                        "\tCreateDate Date,\n" +
                        "\tPRIMARY KEY (GameID),\n" +
                        "\tFOREIGN KEY (BoardID) REFERENCES Boards(BoardID)\n" +
                        ");\n" +
                        "\n",
                "CREATE TABLE IF NOT EXISTS Players(\n" +
                        "\tGameID INT NOT NULL,\n" +
                        "\tplayerNumber INT NOT NULL,\n" +
                        "\t`Location` INT NOT NULL,\n" +
                        "\tColour VARCHAR(50),\n" +
                        "\tPRIMARY KEY (GameID, `Location`, Colour),\n" +
                        "\tFOREIGN KEY (GameID) REFERENCES Games(GameID)\n" +
                        ");\n" +
                        "\n",
                "CREATE TABLE IF NOT EXISTS BoardSquares(\n" +
                        "\tBoardID INT NOT NULL,\n" +
                        "\tSquareNumber INT NOT NULL,\n" +
                        "\tIsWinningSq BOOLEAN,\n" +
                        "\tIsBoostSq BOOLEAN,\n" +
                        "\tPRIMARY KEY (BoardID, SquareNumber),\n" +
                        "\tFOREIGN KEY (BoardID) REFERENCES Boards(BoardID)\n" +
                        ");\n" +
                        "\n",
                "CREATE TABLE IF NOT EXISTS SnakeSquares(\n" +
                        "\tBoardID INT NOT NULL,\n" +
                        "\tSquareNumber INT NOT NULL,\n" +
                        "\t`To` INT NOT NULL,\n" +
                        "\tPRIMARY KEY (BoardID, SquareNumber),\n" +
                        "\tFOREIGN KEY (BoardID, SquareNumber) REFERENCES BoardSquares(BoardID, SquareNumber)\n" +
                        ");\n" +
                        "\n",
                "CREATE TABLE IF NOT EXISTS LadderSquares(\n" +
                        "\tBoardID INT NOT NULL,\n" +
                        "\tSquareNumber INT NOT NULL,\n" +
                        "\t`To` INT NOT NULL,\n" +
                        "\tPRIMARY KEY (BoardID, SquareNumber),\n" +
                        "\tFOREIGN KEY (BoardID, SquareNumber) REFERENCES BoardSquares(BoardID, SquareNumber)\n" +
                        ");"};

        for (int i = 0; i < createQuery.length; i++) {
            st = con.createStatement();
            st.execute(createQuery[i]);
        }

    }


    /**
     * Creates a new board entity in the database with required board size
     * @param size - the width of the board
     * @return - board id of the newly created board
     */
    private int addBoard(int size) throws SQLException{
        String addBoardQuery = "INSERT INTO Boards (Size) VALUES (?);";
        int id = 0;
        PreparedStatement st = null;

        st = con.prepareStatement(addBoardQuery, Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, size);
        st.execute();
        ResultSet key = st.getGeneratedKeys();
        if (key.next()) {
            id = key.getInt(1);
        }

        return id;
    }


    /**
     * creates a new game entity in the database, with the required values.
     * @param boardId - the board to associate with the game
     * @param isGameOver - winning status of the game
     * @param currentPlayer - the player index who is currently playing
     * @return - gameID of the new game
     */
    private int addGame(int boardId, boolean isGameOver, int currentPlayer) throws SQLException{
        String addGameQuery = "INSERT INTO Games (BoardID, isGameOver, CurrentPlayer, CreateDate) VALUES (?, ?, ?, curdate());";
        PreparedStatement st = null;
        int gameID = 0;

        st = con.prepareStatement(addGameQuery, Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, boardId);
        st.setBoolean(2, isGameOver);
        st.setInt(3, currentPlayer);
        st.execute();
        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
            gameID = rs.getInt(1);
        }

        return gameID;
    }

    /**
     * adds a player corresponding to a game
     * @param gameID - the game which needs a player
     * @param playerNumber - The player index to be added
     * @param playerLocation - The player's position on the board
     * @param colour - player's colour
     */
    private void addGamePlayer(int gameID, int playerNumber, int playerLocation, String colour) throws SQLException{
        String addGamePlayerQuery = "INSERT INTO Players VALUES (?, ?, ?, ?);";
        PreparedStatement st = null;

        st = con.prepareStatement(addGamePlayerQuery);
        st.setInt(1, gameID);
        st.setInt(2, playerNumber);
        st.setInt(3, playerLocation);
        st.setString(4, colour);
        st.executeUpdate();

    }

    /**
     * Adds a square to the required board.
     * @param boardID - board in which sqaure is to be added
     * @param sqNum - square number to add
     * @param isWinningSq - if its a winning square
     * @param isBoostSq - if its a boost square
     */
    private void addBoardSquare(int boardID, int sqNum, boolean isWinningSq, boolean isBoostSq) throws SQLException{
        String addBoardSqQuery = "INSERT INTO BoardSquares VALUES (?, ?, ?, ?);";
        PreparedStatement st = null;

        st = con.prepareStatement(addBoardSqQuery);
        st.setInt(1, boardID);
        st.setInt(2, sqNum);
        st.setBoolean(3, isWinningSq);
        st.setBoolean(4, isBoostSq);
        st.execute();

    }

    /**
     * add a snake entity to a board
     * @param boardID - board in which snake is to be added
     * @param sqNum - head of snake
     * @param to - tail of snake
     */
    private void addSnakeSquare(int boardID, int sqNum, int to) throws SQLException{
        String addSnakeSquareQuery = "INSERT INTO SnakeSquares VALUES (?, ?, ?);";
        PreparedStatement st = null;

        st = con.prepareStatement(addSnakeSquareQuery);
        st.setInt(1, boardID);
        st.setInt(2, sqNum);
        st.setInt(3, to);
        st.execute();

    }

    /**
     * add a ladder entity to a board
     * @param boardID - board in which ladder is to be added
     * @param sqNum - bottom of ladder
     * @param to - top of ladder
     */
    private void addLadderSquare(int boardID, int sqNum, int to) throws SQLException{
        String addLadderSquareQuery = "INSERT INTO LadderSquares VALUES (?, ?, ?);";
        PreparedStatement st = null;

        st = con.prepareStatement(addLadderSquareQuery);
        st.setInt(1, boardID);
        st.setInt(2, sqNum);
        st.setInt(3, to);
        st.execute();
    }

    /**
     * Finally a fucntion that would utilize all the previous functions and save the whole game.
     * @param game - the game object to be saved in the database
     */
    public int saveGame(Game game)throws SQLException{
        int size = game.getBoard().getWidth();
        int boardID = addBoard(size);
        int gameID = addGame(boardID, game.isGameOver(), game.getCurrentPlayerIndex());
        List<Triplet<Integer, Integer, String>> players = game.getAllPlayerPositions();
        for (Triplet<Integer, Integer, String> t : players) {
            addGamePlayer(gameID, t.getValue0(), t.getValue1(), t.getValue2());
        }
        List<Square> squares = game.getBoard().getSquares();
        for (Square sq: squares){
            addBoardSquare(boardID, sq.getNumber(), sq.isWinningSquare(), sq.isBoostSquare());
            if (sq.getClass() == SnakeSquare.class){
                addSnakeSquare(boardID, sq.getNumber(), ((SnakeSquare)sq).destination().getNumber());
            }
            if (sq.getClass() == LadderSquare.class){
                addLadderSquare(boardID, sq.getNumber(), ((LadderSquare)sq).destination().getNumber());
            }
        }
        return gameID;
    }

    /**
     * Function that would get all the available game ids in the database
     * @return - List of game ids
     */
    public List<String> getAllGames() throws SQLException{
        String allGamesQuery = "SELECT * FROM games;";
        PreparedStatement st = null;
        ArrayList<String> games = new ArrayList<>();

        st = con.prepareStatement(allGamesQuery);
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            games.add(rs.getInt(1) + " - " + rs.getString(5));
        }

        return games;
    }

    /**
     * Get the asscoiated data of the particular game.
     * @param gameID - the game whihc needs to be get.
     * @return - A triplet including boardID, current player and winning status of the game
     */
    private Triplet<Integer, Integer, Boolean> getGameData(int gameID) throws SQLException{
        String getSizeQuery = "SELECT * FROM Games WHERE GameID = ?";
        PreparedStatement st = null;
        int boardID = 0, currentPlayer = 0;
        boolean isGameOver = false;

        st = con.prepareStatement(getSizeQuery);
        st.setInt(1, gameID);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            boardID = rs.getInt(2);
            currentPlayer = rs.getInt(4);
            isGameOver = rs.getBoolean(3);
        }

        return new Triplet<>(boardID, currentPlayer, isGameOver);
    }

    /**
     * Gets the board width, given the boardid
     * @param boardID - Board whose size is to be taken
     * @return - size of the board
     */
    private int getBoardSize(int boardID) throws SQLException{
        String getSizeQuery = "SELECT Size FROM Boards WHERE BoardID = ?";
        PreparedStatement st = null;
        int size = 0;

        st = con.prepareStatement(getSizeQuery);
        st.setInt(1, boardID);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            size = rs.getInt("Size");
        }

        return size;
    }

    /**
     * Get all e player positions of the particular game
     * @param gameID - ID of the game
     * @return - List of positions.
     */
    private List<Integer> getAllPlayers(int gameID) throws SQLException{
        String getSizeQuery = "SELECT * FROM Players WHERE GameID = ? ORDER BY playerNumber;";
        PreparedStatement st = null;
        ArrayList<Integer> locations = new ArrayList<>();

        st = con.prepareStatement(getSizeQuery);
        st.setInt(1, gameID);
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            locations.add(rs.getInt(3));
        }

        return locations;
    }

    /**
     * Gets all the boostSquare locations of the particular board
     * @param boardID - id of the specified board
     * @return - List of positions.
     */
    private List<Integer> getAllBoostSquares(int boardID) throws SQLException{
        String getBoostsQuery = "SELECT * FROM BoardSquares WHERE BoardID = ? AND IsBoostSq = ?;";
        PreparedStatement st = null;
        ArrayList<Integer> boostSqs = new ArrayList<>();

        st = con.prepareStatement(getBoostsQuery);
        st.setInt(1, boardID);
        st.setBoolean(2, true);
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            boostSqs.add(rs.getInt(2));
        }

        return boostSqs;
    }

    /**
     * get all the snake positions
     * @param boardID - id of the specified board
     * @return - List of pairs, from-to
     */
    private List<Pair<Integer, Integer>> getAllSnakeSquares(int boardID) throws SQLException{
        String getSnakesQuery = "SELECT * FROM SnakeSquares WHERE BoardID = ?;";
        PreparedStatement st = null;
        ArrayList<Pair<Integer, Integer>> snakeSqs = new ArrayList<>();

        st = con.prepareStatement(getSnakesQuery);
        st.setInt(1, boardID);
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            snakeSqs.add(new Pair<>(rs.getInt(2), rs.getInt(3)));
        }
        return snakeSqs;
    }

    /**
     * get all the ladders positions.
     * @param boardID - id of the speciifc board
     * @return - List of from-to pairs
     */
    private List<Pair<Integer, Integer>> getAllLadderSquares(int boardID) throws SQLException{
        String getSnakesQuery = "SELECT * FROM LadderSquares WHERE BoardID = ?;";
        PreparedStatement st = null;
        ArrayList<Pair<Integer, Integer>> ladderSqs = new ArrayList<>();

        st = con.prepareStatement(getSnakesQuery);
        st.setInt(1, boardID);
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            ladderSqs.add(new Pair<>(rs.getInt(2), rs.getInt(3)));
        }
        return ladderSqs;
    }

    /**
     * Builds a game object with the gameID
     * @param gameID - id of the specified game
     * @return - corresponding game object
     * @throws IllegalAccessException - If the game has a winner, it shouldnt be loaded.
     */
    public Game loadGame(int gameID) throws IllegalAccessException, SQLException{

        Triplet<Integer, Integer, Boolean> gameData = getGameData(gameID);
        int boardID = gameData.getValue0(), currentPlayer = gameData.getValue1();
        boolean isGameOver = gameData.getValue2();
        if (isGameOver){
            throw new IllegalAccessException("The game already has a winner");
        }

        int boardSize = getBoardSize(boardID);

        List<Integer> boosts = getAllBoostSquares(boardID);
        List<Pair<Integer, Integer>> snakes = getAllSnakeSquares(boardID);
        List<Pair<Integer, Integer>> ladders = getAllLadderSquares(boardID);


        List<Integer> playerPositions = getAllPlayers(gameID);
        int playerCount = playerPositions.size();

        ArrayList<Integer> snakesArr = new ArrayList<>();
        ArrayList<Integer> laddersArr = new ArrayList<>();
        for(Pair<Integer, Integer> snake: snakes){
            snakesArr.add(snake.getValue0());
            snakesArr.add(snake.getValue1());
        }
        for(Pair<Integer, Integer> ladder: ladders){
            laddersArr.add(ladder.getValue0());
            laddersArr.add(ladder.getValue1());
        }

        return new GameBuilder()
                .withBoardSize(boardSize)
                .withBoosts(boosts.toArray(new Integer[0]))
                .withSnakes(snakesArr.toArray(new Integer[0]))
                .withLadders(laddersArr.toArray(new Integer[0]))
                .withPlayers(playerCount)
                .withStartingPositions(playerPositions.toArray(new Integer[0]))
                .withIsWinningEnabled()
                .withCurrentPlayer(currentPlayer+1)
                .build();
    }

    /**
     * function that updates player's score for a specific game
     */
    public void updatePlayersPosition(int gameID, String colour, int playerLocation) throws SQLException{
        String updateGamePlayerQuery = "UPDATE players SET Location = ? WHERE gameID = ? AND Colour = ?;";
        PreparedStatement st = null;
        st = con.prepareStatement(updateGamePlayerQuery);
        st.setInt(2, gameID);
        st.setString(3, colour);
        st.setInt(1, playerLocation);
        st.executeUpdate();
    }

    /**
     * function to update the current game with the given gameID
     */
    public void updateGame(int gameID, Game game) throws SQLException{

        String updateGameQuery = "UPDATE Games SET isGameOver = ?, CurrentPlayer = ? WHERE gameID = ?;";
        PreparedStatement st = con.prepareStatement(updateGameQuery);
        st.setInt(3, gameID);
        st.setInt(2, game.getCurrentPlayerIndex());
        st.setBoolean(1, game.isGameOver());
        st.executeUpdate();

        List<Triplet<Integer, Integer, String>> playerPositions = game.getAllPlayerPositions();
        for (Triplet<Integer, Integer, String> p : playerPositions){
            updatePlayersPosition(gameID, p.getValue2(), p.getValue1());
        }
    }
}