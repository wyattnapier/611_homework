package Games.Quoridor;

import Games.Core.Player;

public class QuoridorPlayer extends Player {
  private int row;
  private int col;
  private int wallsRemaining;
  private int goalRow;

  // constructor
  public QuoridorPlayer(String name, int startRow, int startCol, int goalRow, int initialWalls) {
    super(name);
    this.row = startRow;
    this.col = startCol;
    this.goalRow = goalRow;
    this.wallsRemaining = initialWalls;
  }

  // getters and setters
  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public int getGoalRow() {
    return goalRow;
  }

  public void setPosition(int newRow, int newCol) {
    this.row = newRow;
    this.col = newCol;
  }

  public int getWallsRemaining() {
    return wallsRemaining;
  }

  public boolean useWall() {
    if (wallsRemaining <= 0)
      return false;
    wallsRemaining--;
    return true;
  }
}