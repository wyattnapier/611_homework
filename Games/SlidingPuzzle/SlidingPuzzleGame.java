package Games.SlidingPuzzle;

import Games.Core.Player;
import Games.Core.Game;
import Games.IO.Input;

public class SlidingPuzzleGame extends Game {
  private Input input;
  private final int MIN_DIMENSION = 2;
  private final int MAX_DIMENSION = 9; // reasonable upper limit to avoid huge
  // boards

  /**
   * constructor for Game
   * 
   * @param playerName name of the player
   */
  public SlidingPuzzleGame(String playerName, Input input) {
    super(playerName, input);
    this.input = input;
  }

  public SlidingPuzzleBoard createBoard(int rows, int cols) {
    return new SlidingPuzzleBoard(rows, cols);
  }

  /**
   * plays a single move of sliding puzzle
   * 
   * @return 1 to continue playing, 0 to indicate a win, -1 to quit
   */
  public int playSingleMove() {
    String selectedTileValue = input.getStringInput("Enter the tile number to move or 'q' to quit: ");
    System.out.println();

    // Cast once at the beginning
    SlidingPuzzleBoard slidingBoard = (SlidingPuzzleBoard) getBoard();

    if (selectedTileValue.equalsIgnoreCase("q")) {
      return -1;
    }
    if (selectedTileValue.equalsIgnoreCase("w")) {
      slidingBoard.setBoardToSolvedState();
      System.out.println(slidingBoard);
      return 0;
    }
    if (slidingBoard.swapTile(selectedTileValue)) {
      Player player = getPlayer();
      player.incrementMoves();
      System.out.println(slidingBoard);
      return slidingBoard.isSolved() ? 0 : 1;
    } else {
      System.out.print("Invalid move. ");
      return 1;
    }
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

  /*
   * Get the minimum board dimension allowed for this game
   */
  public int getMIN_DIMENSION() {
    return MIN_DIMENSION;
  }

  /*
   * Get the maximum board dimension allowed for this game
   */
  public int getMAX_DIMENSION() {
    return MAX_DIMENSION;
  }
}
