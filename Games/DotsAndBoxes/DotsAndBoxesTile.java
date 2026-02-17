package Games.DotsAndBoxes;

import Games.Core.Tile;

public class DotsAndBoxesTile implements Tile {
  private int owner; // TODO: make this an enum so it is 0 is nobody, 1 is player 1 or 2 is player 2

  public DotsAndBoxesTile() {
    owner = 0;
  }

  public boolean isComplete() {
    return false; // TODO: implement
  }

  public String toString() {
    return owner == 0 ? "" : Integer.toString(owner); // either player who owns it or nothing
    // TODO: probably add player name or something?
    // TODO: add color if possible!
  }
}

/**
 * for the dots and boxes game we want a tile to represent a single square on
 * the grid
 * that means we will need booleans for each of the four sides to represent
 * whether or not they have been claimed by a player, and an integer to
 * represent which player has claimed the tile (if any)
 * we will also want a method to check if the tile has been completed (all four
 * sides claimed) and a method to claim a side of the tile for a player
 * we will also need to keep track of the position of the tile on the grid, so
 * we can determine which tiles are adjacent to it and update the game state
 * accordingly when a player claims a side
 * 
 * will still have a board of tiles but each tile will be more abstracted and
 * will extend to have more game specific information
 */