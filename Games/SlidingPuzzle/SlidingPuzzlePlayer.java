package Games.SlidingPuzzle;

import Games.Core.Player;

public class SlidingPuzzlePlayer extends Player {
  private int moves;
  private Player basePlayer;

  public SlidingPuzzlePlayer(Player base) {
    super(base.getPlayerName());
    basePlayer = base;
    moves = 0;
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
