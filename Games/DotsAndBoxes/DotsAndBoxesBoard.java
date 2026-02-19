package Games.DotsAndBoxes;

import java.util.*;
import Games.Core.Board;
import Games.Core.Endpoints;

public class DotsAndBoxesBoard extends Board {
  private Map<Endpoints, DotsAndBoxesEdge> endpointsToEdge = new HashMap<>(); // maps endpoints to actual edge objects
  private Map<DotsAndBoxesEdge, List<DotsAndBoxesTile>> edgeToTiles = new HashMap<>(); // maps edges to the tiles that
                                                                                       // they're a part of
  private int[] verticesOffsets = { 0, 10, 11, 1 }; // could just use single numbers if we set the max number of rows
                                                    // and cols to 9x9
  private DotsAndBoxesTile[][] tiles;
  private int numCompletedTiles;
  // colors for the dots and boxes
  private static final String RESET = "\u001B[0m"; // resets color to default
  private static final String RED = "\u001B[31m"; // Player 1
  private static final String BLUE = "\u001B[34m"; // Player 2

  public DotsAndBoxesBoard(int rows, int cols) {
    super(rows, cols);
    numCompletedTiles = 0;
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
            Endpoints swappedPointPair = new Endpoints(p2, p1);
            endpointsToEdge.put(swappedPointPair, edge);
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
          edgeToTiles.computeIfAbsent(edge, k -> new ArrayList<>()).add(tile); // makes a list if one doesn't exist
        }
      }
    }
  }

  public boolean isValidEdge(Endpoints points) {
    if (points == null || !isWithinBounds(points))
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
      return false;
    DotsAndBoxesEdge currentEdge = endpointsToEdge.get(points);
    if (currentEdge == null || currentEdge.edgeHasOwner())
      return false;
    // know we have a valid edge now, just need to update edge and tile ownership
    currentEdge.setEdgeOwner(currentPlayer);
    List<DotsAndBoxesTile> adjacentTiles = edgeToTiles.get(currentEdge);
    if (adjacentTiles != null) {
      for (DotsAndBoxesTile tile : adjacentTiles) {
        if (tile.setIsComplete(currentPlayer)) {
          incrementNumberOfCompletedTiles();
        }
      }
    }
    return true;
  }

  /**
   * @return true if the entire board is complete
   */
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

  /**
   * function for testing the final win state of the board without having to play
   * the game
   * NOTE: does not properly give a player another turn if they complete a tile
   */
  public void setBoardToSolvedState() {
    List<Endpoints> allEdges = new ArrayList<>(endpointsToEdge.keySet());
    Collections.shuffle(allEdges); // Randomize the order of moves
    DotsAndBoxesOwnership owner = DotsAndBoxesOwnership.PLAYER1;
    for (Endpoints e : allEdges) {
      DotsAndBoxesEdge edge = endpointsToEdge.get(e);
      if (!edge.edgeHasOwner()) {
        // Alternate which player marks an edge
        owner = (owner == DotsAndBoxesOwnership.PLAYER2) ? DotsAndBoxesOwnership.PLAYER1
            : DotsAndBoxesOwnership.PLAYER2;
        markEdge(e, owner);
      }
    }
  }

  public int countNumberOfBoxesOwnedByUser(DotsAndBoxesOwnership owner) {
    int count = 0;
    for (int r = 0; r < board_rows; r++) {
      for (int c = 0; c < board_cols; c++) {
        count += tiles[r][c].getTileOwner() == owner ? 1 : 0;
      }
    }
    return count;
  }

  public void incrementNumberOfCompletedTiles() {
    numCompletedTiles++;
  }

  public int getNumberOfCompletedTiles() {
    return numCompletedTiles;
  }

  // TODO: cleanup
  public String toString() {
    StringBuilder sb = new StringBuilder();
    // ---- Column header ----
    sb.append("\n     ");
    for (int c = 0; c <= board_cols; c++) {
      sb.append(String.format("%-3d", c));
    }
    sb.append("\n\n");

    for (int r = 0; r <= board_rows; r++) {
      sb.append(String.format("%-3d  ", r)); // Row label

      // ----- Dots + Horizontal edges -----
      for (int c = 0; c <= board_cols; c++) {
        sb.append("•");

        if (c < board_cols) {
          int p1 = r * 10 + c;
          int p2 = p1 + 1;
          DotsAndBoxesEdge edge = endpointsToEdge.get(new Endpoints(p1, p2));

          if (edge != null && edge.edgeHasOwner()) {
            // COLOR THE HORIZONTAL EDGE
            String color = (edge.getEdgeOwner() == DotsAndBoxesOwnership.PLAYER1) ? RED : BLUE;
            sb.append(color + "──" + RESET);
          } else {
            sb.append("  ");
          }
        }
      }
      sb.append("\n");

      // ----- Vertical edges + Tile Centers -----
      if (r < board_rows) {
        sb.append("     ");
        for (int c = 0; c <= board_cols; c++) {
          // 1. Handle Vertical Edge
          int p1 = r * 10 + c;
          int p2 = p1 + 10;
          DotsAndBoxesEdge vEdge = endpointsToEdge.get(new Endpoints(p1, p2));

          if (vEdge != null && vEdge.edgeHasOwner()) {
            String color = (vEdge.getEdgeOwner() == DotsAndBoxesOwnership.PLAYER1) ? RED : BLUE;
            sb.append(color + "│" + RESET);
          } else {
            sb.append(" ");
          }

          // 2. Handle Tile Center (The space between vertical edges)
          if (c < board_cols) {
            DotsAndBoxesTile tile = tiles[r][c];
            if (tile.isComplete()) {
              // Check tile.toString() or owner directly
              String color = (tile.toString().equals("1")) ? RED : BLUE;
              sb.append(color + tile.toString() + " " + RESET);
            } else {
              sb.append("  ");
            }
          }
        }
        sb.append("\n");
      }
    }
    return sb.toString();
  }
}
