package Games.DotsAndBoxes;

import java.util.*;
import Games.Core.Board;
import Games.Core.LineEndpoints;
import Games.Enums.DotsAndBoxesOwnershipEnum;

/**
 * this class is for the Board specific to the dots and boxes game
 * it controls most of the game state such as creating all of the edges and
 * tiles and marking them appropriately as well as maintaining the number of
 * completed tiles
 */
public class DotsAndBoxesBoard extends Board {
  private Map<LineEndpoints, DotsAndBoxesEdge> endpointsToEdge = new HashMap<>(); // maps endpoints to actual edge
                                                                                  // objects
  private Map<DotsAndBoxesEdge, List<DotsAndBoxesTile>> edgeToTiles = new HashMap<>(); // maps edges to the tiles that
                                                                                       // they're a part of
  private DotsAndBoxesTile[][] tiles;
  private int numCompletedTiles;

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
          LineEndpoints pointPair = new LineEndpoints(p1, p2);
          if (isValidEdge(pointPair)) {
            DotsAndBoxesEdge edge = new DotsAndBoxesEdge(pointPair);
            endpointsToEdge.put(pointPair, edge);
            LineEndpoints swappedPointPair = new LineEndpoints(p2, p1);
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
          LineEndpoints selectedEdgeEndpoints = new LineEndpoints(p1, p2);
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

  /**
   * checks if edge is valid (points within bounds and adjacent) given endpoints
   * 
   * @param points is an EndpointsEnum
   * @return true if valid else false
   */
  public boolean isValidEdge(LineEndpoints points) {
    if (points == null || !isWithinBounds(points))
      return false;
    boolean isHorizontallyAdjacent = Math.abs(points.p1 - points.p2) == 1;
    boolean isVerticallyAdjacent = Math.abs(points.p1 - points.p2) == 10;
    return isHorizontallyAdjacent || isVerticallyAdjacent;
  }

  /**
   * checks if edge is valid (points within bounds and adjacent) given edge
   * 
   * @param points is a DotsAndBoxesEdge
   * @return true if valid else false
   */
  public boolean isValidEdge(DotsAndBoxesEdge edge) {
    LineEndpoints pointPair = edge.getEdgeEndpoints();
    return isValidEdge(pointPair);
  }

  /**
   * implements abstract superclass method
   * if both points are within bounds then the line/end is in bounds
   * 
   * @param points is a LineEndpoints object consistent of two endpoints of a line
   * @return true if within bounds else false
   */
  public boolean isWithinBounds(LineEndpoints points) {
    return isPointWithinBounds(points.p1) && isPointWithinBounds(points.p2);
  }

  /**
   * implements abstract method of superclass
   * 
   * @param p1 is an int representation of a point e.g. rc
   * @param p2 is an int representation of a point e.g. rc
   * @return true if within bounds else false
   */
  public boolean isWithinBounds(int p1, int p2) {
    return isPointWithinBounds(p1) && isPointWithinBounds(p2);
  }

  /**
   * checks if point is within bounds of the board
   * 
   * @param p point calculated from (row*10+col)
   * @return true if within bounds else false
   */
  public boolean isPointWithinBounds(int p) {
    int r = p / 10;
    int c = p % 10;
    return (0 <= r && r <= board_rows) && (0 <= c && c <= board_cols);
  }

  /**
   * marks an edge as owned by the current player and increments the number of
   * completed tiles on the board if this closes tile(s).
   * 
   * @param points        endpoints of the edge that will be marked
   * @param currentPlayer player who's turn it is
   * @return true if successfully marks and false otherwise
   */
  public boolean markEdge(LineEndpoints points, DotsAndBoxesOwnershipEnum currentPlayer) {
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
   * because it is just a simulation of getting to solved state in a
   * semi-realistic way
   */
  public void setBoardToSolvedState() {
    List<LineEndpoints> allEdges = new ArrayList<>(endpointsToEdge.keySet());
    Collections.shuffle(allEdges); // Randomize the order of moves
    DotsAndBoxesOwnershipEnum owner = DotsAndBoxesOwnershipEnum.PLAYER1;
    for (LineEndpoints e : allEdges) {
      DotsAndBoxesEdge edge = endpointsToEdge.get(e);
      if (!edge.edgeHasOwner()) {
        // Alternate which player marks an edge
        owner = (owner == DotsAndBoxesOwnershipEnum.PLAYER2) ? DotsAndBoxesOwnershipEnum.PLAYER1
            : DotsAndBoxesOwnershipEnum.PLAYER2;
        markEdge(e, owner);
      }
    }
  }

  /**
   * returns number of boxes owned by input user
   * 
   * @param owner Player1 or Player2 or Nobody
   * @return number of boxes owned by the input owner
   */
  public int countNumberOfBoxesOwnedByUser(DotsAndBoxesOwnershipEnum owner) {
    int count = 0;
    for (int r = 0; r < board_rows; r++) {
      for (int c = 0; c < board_cols; c++) {
        count += tiles[r][c].getTileOwner() == owner ? 1 : 0;
      }
    }
    return count;
  }

  /**
   * increment number of completed tiles in board
   */
  public void incrementNumberOfCompletedTiles() {
    numCompletedTiles++;
  }

  /**
   * @return integer number of completed tiles in board
   */
  public int getNumberOfCompletedTiles() {
    return numCompletedTiles;
  }

  /**
   * converts board to string and uses colors for clarity
   * 
   * @return String representation of board
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    // column header
    sb.append("\n     ");
    for (int c = 0; c <= board_cols; c++) {
      sb.append(String.format("%-3d", c));
    }
    sb.append("\n\n");

    for (int r = 0; r <= board_rows; r++) {
      sb.append(String.format("%-3d  ", r)); // row label
      // dots and horizontal edges
      for (int c = 0; c <= board_cols; c++) {
        sb.append("•");
        if (c < board_cols) {
          int p1 = r * 10 + c;
          int p2 = p1 + 1;
          DotsAndBoxesEdge edge = endpointsToEdge.get(new LineEndpoints(p1, p2));

          if (edge != null && edge.edgeHasOwner()) {
            sb.append(edge.toString());
          } else {
            sb.append("  ");
          }
        }
      }
      sb.append("\n");

      // vertical edges and tile centers
      if (r < board_rows) {
        sb.append("     ");
        for (int c = 0; c <= board_cols; c++) {
          // Handle Vertical Edge
          int p1 = r * 10 + c;
          int p2 = p1 + 10;
          DotsAndBoxesEdge vEdge = endpointsToEdge.get(new LineEndpoints(p1, p2));
          if (vEdge != null && vEdge.edgeHasOwner()) {
            sb.append(vEdge.toString());
          } else {
            sb.append(" ");
          }
          // handle tile center
          if (c < board_cols) {
            DotsAndBoxesTile tile = tiles[r][c];
            if (tile.isComplete()) {
              String color = tile.getTileOwner().getColor();
              sb.append(color + tile.toString() + " " + DotsAndBoxesOwnershipEnum.getReset());
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
