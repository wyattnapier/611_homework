package Games.DotsAndBoxes;

import java.util.*;
import Games.Core.Board;
import Games.Core.Endpoints;
import Games.Core.Player;

public class DotsAndBoxesBoard extends Board {
  private Map<Endpoints, DotsAndBoxesEdge> endpointsToEdge = new HashMap<>(); // maps endpoints to actual edge objects
  private Map<DotsAndBoxesEdge, DotsAndBoxesTile> edgeToTiles = new HashMap<>(); // maps edges to the tiles that they're
                                                                                 // a part of
  private int[] verticesOffsets = { 0, 10, 11, 1 }; // could just use single numbers if we set the max number of rows
                                                    // and cols to 9x9
  private DotsAndBoxesTile[][] tiles;

  public DotsAndBoxesBoard(int rows, int cols) {
    super(rows, cols);
    int[] edgeOffsets = { 1, 10 };
    // populate the map of endpoints to edges
    for (int r = 0; r <= rows; r++) {
      for (int c = 0; c <= cols; c++) {
        for (int offset : edgeOffsets) {
          int p1 = r * 10 + c;
          int p2 = p1 + offset;
          Endpoints pointPair = new Endpoints(p1, p2);
          if (isValidEdge(pointPair)) {
            DotsAndBoxesEdge edge = new DotsAndBoxesEdge(pointPair);
            endpointsToEdge.put(pointPair, edge);
          }
        }
      }
    }
    // loop through all top left corners of boxes and create tiles to populate map
    // of edge to tile and the tile array
    tiles = new DotsAndBoxesTile[board_rows][board_cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        int topLeftVertex = r * 10 + c;
        DotsAndBoxesEdge[] tileEdges = new DotsAndBoxesEdge[4];
        for (int offsetIndex = 0; offsetIndex < verticesOffsets.length; offsetIndex++) {
          int p1 = topLeftVertex + verticesOffsets[offsetIndex];
          int p2 = topLeftVertex + verticesOffsets[(offsetIndex + 1) % verticesOffsets.length];
          Endpoints selectedEdgeEndpoints = new Endpoints(p1, p2);
          DotsAndBoxesEdge selectedEdge = endpointsToEdge.get(selectedEdgeEndpoints);
          tileEdges[offsetIndex] = selectedEdge;
        }
        DotsAndBoxesTile tile = new DotsAndBoxesTile(topLeftVertex, tileEdges);
        tiles[r][c] = tile;
        for (DotsAndBoxesEdge edge : tileEdges) {
          edgeToTiles.put(edge, tile);
        }
      }
    }
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

  /**
   * 
   * @param points
   * @param currentPlayer
   * @return true if successfully marks and false otherwise
   */
  public boolean markEdge(Endpoints points, DotsAndBoxesOwnership currentPlayer) {
    if (!isValidEdge(points))
      return false; // invalid edge
    DotsAndBoxesEdge currentEdge = endpointsToEdge.get(points);
    if (currentEdge.edgeHasOwner())
      return false; // edge has already been claimed
    // update edge and tile ownership
    currentEdge.setEdgeOwner(currentPlayer);
    DotsAndBoxesTile currentTile = edgeToTiles.get(currentEdge);
    currentTile.setIsComplete(currentPlayer);
    return true;
  }

  public boolean isSolved() {
    for (int i = 0; i < board_rows; i++) {
      for (int j = 0; j < board_cols; j++) {
        if (!tiles[i][j].isComplete()) {
          return false;
        }
      }
    }
    return true;
  }

  public void setBoardToSolvedState() {
    return; // TODO: implement when Dots and Boxes is implemented by executing random turns
  }

  // TODO: clean up and test after implementing some game loop
  public String toString() {
    StringBuilder sb = new StringBuilder();
    // ---- Column header ----
    sb.append("\n     ");
    for (int c = 0; c <= board_cols; c++) {
      sb.append(String.format("%-3d", c));
    }
    sb.append("\n\n");

    // ---- Board rendering ----
    for (int r = 0; r <= board_rows; r++) {

      // Row label for dots line
      sb.append(String.format("%-3d  ", r));

      // ----- Dots + Horizontal edges -----
      for (int c = 0; c <= board_cols; c++) {

        sb.append("•");

        if (c < board_cols) {
          int p1 = r * 10 + c;
          int p2 = p1 + 1;
          Endpoints e = new Endpoints(p1, p2);
          DotsAndBoxesEdge edge = endpointsToEdge.get(e);

          if (edge != null && edge.edgeHasOwner()) {
            sb.append("──");
          } else {
            sb.append("  ");
          }
        }
      }
      sb.append("\n");

      // ----- Vertical edges (skip last dot row) -----
      if (r < board_rows) {
        sb.append("     ");
        for (int c = 0; c <= board_cols; c++) {
          int p1 = r * 10 + c;
          int p2 = p1 + 10;
          Endpoints e = new Endpoints(p1, p2);
          DotsAndBoxesEdge edge = endpointsToEdge.get(e);
          if (edge != null && edge.edgeHasOwner()) {
            sb.append("│  ");
          } else {
            sb.append("   ");
          }
        }
        sb.append("\n");
      }
    }
    return sb.toString();
  }
  // public String toString() {
  // String dot = "•";
  // return "NOTE: still need to implement Dots and Boxes board representation";
  // // TODO: implement when Dots and Boxes
  // // is implemented

  // }
}
