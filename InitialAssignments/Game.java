import java.util.Scanner;

/**
 * used to control the game flow
 */
public class Game {

  private Player player;
  private Board board;

  /**
   * constructor for Game
   * 
   * @param playerName name of the player
   */
  public Game(String playerName) {
    player = new Player(playerName);
  }

  /**
   * plays a single game of sliding puzzle
   * 
   * @param rows number of rows for the board
   * @param cols number of columns for the board
   * @param scan Scanner object for user input
   */
  public void playSingleGame(int rows, int cols, Scanner scan) {
    // ensure that game isn't already solved when board is created
    do {
      board = new Board(rows, cols);
    } while (board.isSolved());
    System.out.println(board);

    player.resetNumberOfMoves();

    int continuePlaying = 1; // 1 to continue, 0 to win, -1 to quit
    while (continuePlaying == 1) {
      continuePlaying = playSingleMove(scan);
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
   * @param scan Scanner object for user input
   * @return 1 to continue playing, 0 to indicate a win, -1 to quit
   */
  public int playSingleMove(Scanner scan) {
    System.out.print("Enter the tile number to move: ");
    String selectedTileValue = scan.nextLine();
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
   * 
   * @param scan Scanner object for user input
   */
  public void setupAndPlayMultipleGames(Scanner scan) {
    int rows = getValidBoardDimension(scan, "rows");
    int cols = getValidBoardDimension(scan, "columns");
    System.out.println();
    playSingleGame(rows, cols, scan);

    String playAgainInput;
    do {
      System.out.print("Would you like to play again? [y/n] ");
      playAgainInput = scan.nextLine();
      if (playAgainInput.equalsIgnoreCase("y")) {
        playSingleGame(rows, cols, scan);
      } else if (playAgainInput.equalsIgnoreCase("n")) {
        System.out.println("Thanks for playing!");
      } else {
        System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
      }
    } while (playAgainInput.equalsIgnoreCase("y"));
  }

  /**
   * Prompts the user for a valid board dimension (rows or columns)
   * 
   * @param scan          the Scanner for user input
   * @param dimensionName the name of the dimension ("rows" or "columns")
   * @return a valid board dimension (2 <= dimension <= 9)
   */
  private int getValidBoardDimension(Scanner scan, String dimensionName) {
    final int MIN_DIMENSION = 2;
    final int MAX_DIMENSION = 9; // reasonable upper limit to avoid huge boards

    while (true) {
      System.out.print("\n" + player.getPlayerName() + ", how many " + dimensionName + " for the board? ");

      // Check if next input is an integer
      if (!scan.hasNextInt()) {
        System.out
            .println("Invalid input. Please enter a number between " + MIN_DIMENSION + " and " + MAX_DIMENSION + ".");
        scan.nextLine(); // consume the invalid input
        continue;
      }

      int dimension = scan.nextInt();
      scan.nextLine(); // consume the newline

      // Validate range
      if (dimension < MIN_DIMENSION || dimension > MAX_DIMENSION) {
        System.out.println("Invalid input. Board " + dimensionName + " must be between " + MIN_DIMENSION + " and "
            + MAX_DIMENSION + ".");
        continue;
      }

      return dimension;
    }
  }

}
