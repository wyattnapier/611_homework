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
   * @return true if tile is completely surrounded by walls
   */
  public boolean isComplete() {
    for (QuoridorEdge e : edges) {
      if (!e.isWall())
        return false;
    }
    return true;
  }
}
