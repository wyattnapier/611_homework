package Games.Core;

public class Player {
  private String name;
  private int moves;
  private int gamesWon;
  private int boxesOwned;

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
   * Get the number of moves made by the player
   * 
   * @return number of moves
   */
  public int getNumberOfMoves() {
    return moves;
  }

  public void incrementNumberOfBoxesOwned() {
    boxesOwned++;
  }

  public void resetNumberOfBoxesOwned() {
    boxesOwned = 0;
  }

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
