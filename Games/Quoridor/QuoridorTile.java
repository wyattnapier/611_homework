package Games.Quoridor;

import Games.Core.Tile;
import Games.Core.CoordPoint;

public class QuoridorTile implements Tile {
  private int row, col;
  private QuoridorEdge[] edges; // order edge list as up, right, left, right

  QuoridorTile(CoordPoint topLeft, QuoridorEdge[] tileEdges) {
    this.row = topLeft.getRow();
    this.col = topLeft.getCol();
    edges = tileEdges;
  }

  /**
   * method of interface that is used to determine if a tile is complete (e.g. all
   * edges have been marked in dots and boxes or box is in correct final position
   * for sliding puzzle) but not sure what use case is in this game
   */
  // TODO: find use case for implementation
  // Implementation use case idea: set all tiles to false and set an end tile to
  // true if a player is in it?
  public boolean isComplete() {
    return true;
  }
}
