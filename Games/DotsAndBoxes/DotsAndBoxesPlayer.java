package Games.DotsAndBoxes;

import Games.Core.Player;

public class DotsAndBoxesPlayer extends Player {
  private int boxesOwned;
  private Player basePlayer;

  public DotsAndBoxesPlayer(Player base) {
    super(base.getPlayerName());
    basePlayer = base;
    boxesOwned = 0;
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
   * overrides subclass method by calling the method of the superclass on the base
   * player that holds the stats for the player
   */
  public void incrementGamesWon() {
    this.basePlayer.incrementGamesWon();
  }

  /**
   * since we're updating the number of games won for the base player we should
   * also be using that as the total number of games won
   */
  public int getNumberOfGamesWon() {
    return this.basePlayer.getNumberOfGamesWon();
  }
}
