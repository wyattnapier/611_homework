package Games.Core;

/**
 * Abstract class representing a generic board game. Contains common
 * functionality used to control the game flow
 */
public interface Game {

  /**
   * plays a single game of the specific game type
   * 
   * @param rows number of rows for the board
   * @param cols number of columns for the board
   */
  public abstract void playSingleGame(int rows, int cols);

  /**
   * plays a single move of the specific game type
   * 
   * @return 1 to continue playing, 0 to indicate a win, -1 to quit
   */
  public abstract int playSingleMove();

  /**
   * sets up and plays multiple games, asking user if they want to play again
   * after each game
   */
  public abstract void setupAndPlayMultipleGames();

  /**
   * Prompts the user for a valid board dimension (rows or columns)
   * 
   * @param dimensionName the name of the dimension ("rows" or "columns")
   * @return a valid board dimension (MIN_DIMENSION <= dimension <= MAX_DIMENSION)
   */
  public abstract int getValidBoardDimension(String dimensionName);
}
