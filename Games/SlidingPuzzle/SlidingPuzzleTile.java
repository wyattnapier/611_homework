package Games.SlidingPuzzle;

import Games.Core.Tile;

/**
 * class implementing the general sliding puzzle tile and the lgoic for updating
 * its position and checking if it is in the correct final positions
 */
public class SlidingPuzzleTile implements Tile {
  private int value; // value of the tile
  private int x, y; // position of the tile
  private int dest_x, dest_y; // destination position of the tile when game is solved

  /**
   * Constructor for Tile
   * 
   * @param val        value of the tile
   * @param x_pos      current x position of the tile
   * @param y_pos      current y position of the tile
   * @param dest_x_pos destination x position of the tile when game is solved
   * @param dest_y_pos destination y position of the tile when game is solved
   */
  public SlidingPuzzleTile(int val, int x_pos, int y_pos, int dest_x_pos, int dest_y_pos) {
    value = val;
    x = x_pos;
    y = y_pos;
    dest_x = dest_x_pos;
    dest_y = dest_y_pos;
  }

  /**
   * Update the current position of the tile
   * 
   * @param newX new x position
   * @param newY new y position
   */
  public void updateTilePosition(int newX, int newY) {
    x = newX;
    y = newY;
  }

  /**
   * Check if the tile is in its correct final position
   * 
   * @return true if in correct position, false otherwise
   */
  public boolean isComplete() {
    return x == dest_x && y == dest_y;
  }

  /**
   * returns display value of the tile
   */
  public String toString() {
    return Integer.toString(value);
  }
}
