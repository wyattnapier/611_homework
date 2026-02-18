package Games.DotsAndBoxes;

import java.util.*;
import Games.Core.Board;
import Games.Core.Endpoints;
import Games.Core.Tile;

public class DotsAndBoxesBoard extends Board {
  private Map<Endpoints, DotsAndBoxesEdge> endpointsToEdge; // maps endpoints to actual edge objects
  private Map<DotsAndBoxesEdge, List<DotsAndBoxesTile>> edgeToTiles; // maps edges to the tiles that they're a part of
  private int[] verticesOffsets = { 0, 10, 11, 1 }; // could just use single numbers if we set the max number of rows
                                                    // and cols to 9x9
  private Tile[][] tiles;

  public DotsAndBoxesBoard(int rows, int cols) {
    super(rows, cols);
    int[] edgeOffsets = { 1, 10 };
    // TODO: populate the map of ednpionts to edges
    for (int r = 0; r <= rows; r++) {
      for (int j = 0; j <= cols; j++) {
        for (int offset : edgeOffsets) {
          int p1 = r * 10 + j;
          int p2 = p1 + offset;
          Endpoints pointPair = new Endpoints(p1, p2);
          if (isValidEdge(pointPair)) {
            DotsAndBoxesEdge edge = new DotsAndBoxesEdge(pointPair);
            endpointsToEdge.put(pointPair, edge);
          }
        }
      }
    }
    // TODO: iterate through all top left corners and create tiles to populate map
    // of edge to tile and the tile array
  }

  public boolean isValidEdge(Endpoints points) {
    if (!isWithinBounds(points))
      return false;
    boolean isHorizontallyAdjacent = Math.abs(points.p1 - points.p2) == 1;
    boolean isVerticallyAdjacent = Math.abs(points.p1 - points.p2) == 10;
    return isHorizontallyAdjacent || isVerticallyAdjacent;
  }

  public boolean isValidEdge(DotsAndBoxesEdge edge) {
    Endpoints pointPair = edge.getEdgeEndpoints();
    return isValidEdge(pointPair);
  }

  public boolean isWithinBounds(Endpoints points) {
    return isPointWithinBounds(points.p1) && isPointWithinBounds(points.p2);
  }

  public boolean isPointWithinBounds(int p) {
    int r = p / 10;
    int c = p % 10;
    return (0 <= r && r <= board_rows) && (0 <= c && c <= board_cols);
  }

  public boolean isSolved() {
    for (int i = 0; i < board_rows; i++) {
      for (int j = 0; j < board_cols; j++) {
        if (!tiles[i][j].isComplete()) {
          return false;
        }
      }
    }
    return true; // TODO: implement when Dots and Boxes is implemented
  }

  public void setBoardToSolvedState() {
    return; // TODO: implement when Dots and Boxes is implemented by executing random turns
  }

  public String toString() {
    String dot = "•";
    return "NOTE: still need to implement Dots and Boxes board representation"; // TODO: implement when Dots and Boxes
                                                                                // is implemented
  }
}
