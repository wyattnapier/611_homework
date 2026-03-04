package Games.Core;

/**
 * This class is used to manage players of games. It stores relevant information
 * about them and allows that information to be updated and
 * retrieved via getters and setters.
 */
public class Player {
  private String name;
  private int gamesWon; // overall games

  /**
   * Constructor for Player
   * 
   * @param playerName name of the player
   */
  public Player(String playerName) {
    name = playerName;
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
