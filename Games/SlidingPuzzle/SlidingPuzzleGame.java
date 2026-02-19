package Games.SlidingPuzzle;

import Games.Core.Player;
import Games.Core.Game;
import Games.IO.Input;

/**
 * Class representing the sliding puzzle game, which extends the generic Game
 * class and implements the specific logic for playing a sliding puzzle game. It
 * handles user
 */
public class SlidingPuzzleGame implements Game {
  private Player player;
  private SlidingPuzzleBoard board;
  private Input input;
  private final int MIN_DIMENSION = 2;
  private final int MAX_DIMENSION = 9; // reasonable upper limit to avoid huge
  // boards

  /**
   * constructor for Game
   * 
   * @param playerName name of the player
   */
  public SlidingPuzzleGame(Player player, Input input) {
    this.player = player;
    this.input = input;
  }

  /**
   * sets up and plays multiple games, asking user if they want to play again
   * after each game
   */
  public void setupAndPlayMultipleGames() {
    int rows = getValidBoardDimension("rows");
    int cols = getValidBoardDimension("columns");
    System.out.println();
    // control flow for playing multiple games
    String playAgainInput;
    do {
      playSingleGame(rows, cols);
      playAgainInput = input.getPlayAgainInputString();
      if (playAgainInput.equalsIgnoreCase("n")) {
        System.out.println("Thanks for playing!");
        break;
      }
    } while (playAgainInput.equalsIgnoreCase("y"));
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
      board = new SlidingPuzzleBoard(rows, cols);
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
    String selectedTileValue = input.getStringInput("Enter the tile number to move or 'q' to quit: ");
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
      Player player = getPlayer();
      player.incrementMoves();
      System.out.println(board);
      return board.isSolved() ? 0 : 1;
    } else {
      System.out.print("Invalid move. ");
      return 1;
    }
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
   * @return a valid board dimension (MIN_DIMENSION <= dimension <=
   *         MAX_DIMENSION)
   */
  public int getValidBoardDimension(String dimensionName) {
    return input.getIntForBoardDimension(dimensionName, MIN_DIMENSION, MAX_DIMENSION);
  }
}
