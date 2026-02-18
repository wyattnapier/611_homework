package Games.DotsAndBoxes;

import Games.Core.Tile;

public class DotsAndBoxesTile implements Tile {
  private DotsAndBoxesOwnership owner; // TODO: make this an enum so it is 0 is nobody, 1 is player 1 or 2 is player 2
  private int[] vertices; // list of corners in square starting top left and going clockwise
  private DotsAndBoxesEdge[] edges; // order edge list as up, right, left, right
  private int[] verticesOffsets = { 0, 10, 11, 1 }; // could just use single numbers if we set the max number of rows
                                                    // and cols to 9x9

  /**
   * @param topLeftCornerValue is the location of the top left corner vertex
   * @param edges              is a list of the references to the edges for the
   *                           tile
   */
  public DotsAndBoxesTile(int topLeftCornerValue, DotsAndBoxesEdge[] edges) {
    owner = DotsAndBoxesOwnership.NOBODY;
    vertices = new int[verticesOffsets.length];
    this.edges = edges;
    for (int i = 0; i < verticesOffsets.length; i++) {
      vertices[i] = topLeftCornerValue + verticesOffsets[i];
    }
  }

  public boolean isComplete() {
    return owner != DotsAndBoxesOwnership.NOBODY;
  }

  // set a box to be complete if all edges in it are complete after marking an edge
  // public void setIsComplete() {
    
  // }

  // TODO: mark an edge complete which will then also set ownership

  // TODO: is it possible to change colors?
  // TODO: better labeling?
  public String toString() {
    switch (owner) {
      case PLAYER1:
        return "1";
      case PLAYER2:
        return "2";
      default:
        return "";
    }
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