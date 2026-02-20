package Games.Core;

import Games.Enums.MoveOutcome;
import Games.IO.Input;

/**
 * Abstract class representing a generic board game. Contains common
 * functionality used to control the game flow
 */
public abstract class Game {
  protected Input input;

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
  public abstract MoveOutcome playSingleMove();

  /**
   * sets up and plays multiple games, asking user if they want to play again
   * after each game
   */
  public void setupAndPlayMultipleGames() {
    int minDim = getMinDimension();
    int maxDim = getMaxDimension();
    int rows = input.getIntForBoardDimension("rows", minDim, maxDim);
    int cols = input.getIntForBoardDimension("columns", minDim, maxDim);
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
   * since boards are square, the dimension limits are the same for rows and
   * columns and just need one method to get each. This is for the minimum option
   * for when user inputs requested dimension
   * 
   * @return minimum board dimension (e.g. 1 for dots and boxes)
   */
  abstract public int getMinDimension();

  /**
   * since boards are square, the dimension limits are the same for rows and
   * columns and just need one method to get each. This is for the max option
   * for when user inputs requested dimension
   * 
   * @return max board dimension (e.g. 9 for dots and boxes)
   */
  abstract public int getMaxDimension();
}
