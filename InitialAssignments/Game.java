import IO.Input;

/**
 * used to control the game flow
 */
public class Game {

  private Player player;
  private Board board;
  private Input input;
  private final int MIN_DIMENSION = 2;
  private final int MAX_DIMENSION = 9; // reasonable upper limit to avoid huge boards

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
      board = new Board(rows, cols);
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
   * plays a single move of sliding puzzle
   * 
   * @return 1 to continue playing, 0 to indicate a win, -1 to quit
   */
  public int playSingleMove() {
    String selectedTileValue = input.getStringInput("Enter the tile number to move: ");
    System.out.println();
    if (selectedTileValue.equalsIgnoreCase("q")) {
      return -1;
    }
    if (selectedTileValue.equalsIgnoreCase("w")) {
      board.setBoardToSolvedState();
      System.out.println(board);
      return 0;
    }
    if (board.swapTile(selectedTileValue)) {
      player.incrementMoves();
      System.out.println(board);
      return board.isSolved() ? 0 : 1;
    } else {
      System.out.print("Invalid move. ");
      return 1;
    }
  }

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
   * Prompts the user for a valid board dimension (rows or columns)
   * 
   * @param dimensionName the name of the dimension ("rows" or "columns")
   * @return a valid board dimension (MIN_DIMENSION <= dimension <= MAX_DIMENSION)
   */
  private int getValidBoardDimension(String dimensionName) {
    while (true) {
      String prompt = "Enter the number of " + dimensionName + " for the board (" + MIN_DIMENSION + "-" + MAX_DIMENSION
          + "): ";
      int dimension = input.getIntForBoardDimension(prompt, MIN_DIMENSION, MAX_DIMENSION);
      return dimension;
    }
  }
}
