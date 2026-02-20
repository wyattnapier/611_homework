package Games.Core;

/**
 * This class is used to manage players of games. It stores relevant information
 * about them and allows that information to be updated and
 * retrieved via getters and setters.
 */
public class Player {
  private String name;
  private int moves; // mainly used for sliding puzzle
  private int gamesWon; // overall games
  private int boxesOwned; // mainly used for dots and boxes

  /**
   * Constructor for Player
   * 
   * @param playerName name of the player
   */
  public Player(String playerName) {
    name = playerName;
    moves = 0;
    boxesOwned = 0;
    gamesWon = 0;
  }

  /**
   * Get the player's name
   * 
   * @return player's name
   */
  public String getPlayerName() {
    return name;
  }

  /**
   * Increment the number of moves made by the player
   */
  public void incrementMoves() {
    moves++;
  }

  /**
   * Reset the number of moves made by the player to zero
   */
  public void resetNumberOfMoves() {
    moves = 0;
  }

  /**
   * Get the number of moves made by the players
   * 
   * @return number of moves
   */
  public int getNumberOfMoves() {
    return moves;
  }

  /**
   * increments the number of boxes that this user owns by 1
   */
  public void incrementNumberOfBoxesOwned() {
    boxesOwned++;
  }

  /**
   * @param count new number of boxxes owned by this player
   */
  public void setNumberOfBoxesOwned(int count) {
    boxesOwned = count;
  }

  /**
   * resets the instance variable boxesOwned that represents the number of boxes a
   * user completed in a dots and boxes game
   */
  public void resetNumberOfBoxesOwned() {
    boxesOwned = 0;
  }

  /**
   * @return number of boxes that a user completed in dots and boxes
   */
  public int getNumberOfBoxesOwned() {
    return boxesOwned;
  }

  /**
   * Increment the number of games won by the player
   */
  public void incrementGamesWon() {
    gamesWon++;
  }

  /**
   * Get the number of games won by the player
   * 
   * @return number of games won
   */
  public int getNumberOfGamesWon() {
    return gamesWon;
  }
}
