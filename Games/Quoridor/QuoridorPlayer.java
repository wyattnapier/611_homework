package Games.Quoridor;

import Games.Core.Player;

/**
 * Manages stats for quoridor player and connects it to the larger baseplayer
 * class
 */
public class QuoridorPlayer extends Player {
  private Player basePlayer;
  private int row;
  private int col;
  private int wallsRemaining;
  private int goalRow;

  // constructor
  public QuoridorPlayer(Player base, int startRow, int startCol, int goalRow, int initialWalls) {
    super(base.getPlayerName());
    basePlayer = base;
    this.row = startRow;
    this.col = startCol;
    this.goalRow = goalRow;
    this.wallsRemaining = initialWalls;
  }

  /**
   * 
   * @return player's current row on board
   */
  public int getRow() {
    return row;
  }

  /**
   * 
   * @return player's current column on board
   */
  public int getCol() {
    return col;
  }

  /**
   * 
   * @return player's goal row on board
   */
  public int getGoalRow() {
    return goalRow;
  }

  /**
   * sets players position on the board
   * 
   * @param newRow
   * @param newCol
   */
  public void setPosition(int newRow, int newCol) {
    this.row = newRow;
    this.col = newCol;
  }

  /**
   * 
   * @return number of walls that player has left to play
   */
  public int getWallsRemaining() {
    return wallsRemaining;
  }

  /**
   * sets number of walls that player has left to play
   * 
   * @param newWallsRemaining
   */
  public void setWallsRemaining(int newWallsRemaining) {
    wallsRemaining = newWallsRemaining;
  }

  /**
   * decrements number of walls that player has left to play
   */
  public void useWall() {
    wallsRemaining--;
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