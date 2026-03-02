package Games.Quoridor;

import Games.Core.Tile;
import Games.Core.CoordPoint;

public class QuoridorTile implements Tile {
  private int row, col;
  private QuoridorEdge[] edges; // order edge list as top, right, bottom, left

  QuoridorTile(CoordPoint topLeft, QuoridorEdge[] tileEdges) {
    this.row = topLeft.getRow();
    this.col = topLeft.getCol();
    edges = tileEdges;
  }

  // returns tile's top edge
  public QuoridorEdge getTopEdge() {
    return edges[0];
  }

  // returns tile's right edge
  public QuoridorEdge getRightEdge() {
    return edges[1];
  }

  // returns tile's bottom edge
  public QuoridorEdge getBottomEdge() {
    return edges[2];
  }

  // returns tile's left edge
  public QuoridorEdge getLeftEdge() {
    return edges[3];
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
