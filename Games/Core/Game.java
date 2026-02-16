package Games.Core;

import Games.IO.Input;

/**
 * used to control the game flow
 */
public abstract class Game {

  private Player player;
  private Board board;
  private Input input;

  /**
   * constructor for Game
   * 
   * @param playerName name of the player
   */
  public Game(String playerName, Input input) {
    player = new Player(playerName);
    this.input = input;
  }

  /**
   * plays a single game of sliding puzzle
   * 
   * @param rows number of rows for the board
   * @param cols number of columns for the board
   */
  public void playSingleGame(int rows, int cols) {
    // ensure that game isn't already solved when board is created
    do {
      board = createBoard(rows, cols);
    } while (board.isSolved());
    System.out.println(board);

    player.resetNumberOfMoves();

    int continuePlaying = 1; // 1 to continue, 0 to win, -1 to quit
    while (continuePlaying == 1) {
      continuePlaying = playSingleMove();
    }

    // game end message
    if (continuePlaying == 0) {
      player.incrementGamesWon();
      System.out.println("Congratulations! You've won the game!");
      System.out.println("It only took you " + player.getNumberOfMoves() + " moves to win your last game!");
      System.out.println("You've won " + player.getNumberOfGamesWon() + " games!\n");

    } else if (continuePlaying == -1) {
      System.out.println("You won " + player.getNumberOfGamesWon() + " games!");
    }
  }

  /**
   * creates and returns a new board for the appropriate game type with the
   * specified dimensions
   * 
   * @param rows number of rows for the board
   * @param cols number of columns for the board
   * @return a new Board object with the specified dimensions
   */
  public abstract Board createBoard(int rows, int cols);

  /**
   * plays a single move of sliding puzzle
   * 
   * @return 1 to continue playing, 0 to indicate a win, -1 to quit
   */
  public abstract int playSingleMove();

  /**
   * sets up and plays multiple games, asking user if they want to play again
   * after each game
   */
  public void setupAndPlayMultipleGames() {
    int rows = getValidBoardDimension("rows");
    int cols = getValidBoardDimension("columns");
    System.out.println();
    playSingleGame(rows, cols);

    String playAgainInput;
    do {
      playAgainInput = input.getPlayAgainInputString();
      if (playAgainInput.equalsIgnoreCase("n")) {
        System.out.println("Thanks for playing!");
        break;
      }
      playSingleGame(rows, cols); // implicit else, play again if input is 'y' since getPlayAgainInputString
                                  // ensures valid input
    } while (playAgainInput.equalsIgnoreCase("y"));
  }

  /**
   * Get the current board
   * 
   * @return the Board object
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Get the current player
   * 
   * @return the Player object
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Prompts the user for a valid board dimension (rows or columns)
   * 
   * @param dimensionName the name of the dimension ("rows" or "columns")
   * @return a valid board dimension (MIN_DIMENSION <= dimension <= MAX_DIMENSION)
   */
  public abstract int getValidBoardDimension(String dimensionName);

  /*
   * Get the minimum board dimension allowed for this game
   */
  public abstract int getMIN_DIMENSION();

  /*
   * Get the maximum board dimension allowed for this game
   */
  public abstract int getMAX_DIMENSION();
}
