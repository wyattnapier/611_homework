package Games.Quoridor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Games.Core.Board;
import Games.Core.LineEndpoints;
import Games.Enums.DotsAndBoxesOwnershipEnum;

public class QuoridorBoard extends Board {
  // set size of board and walls per player as constants
  public final int DEFAULT_SIZE = 9;
  public final int INITIAL_WALLS_PER_PLAYER = 10;

  // board of tiles and players
  private final QuoridorTile[][] tiles;
  private final QuoridorPlayer player1;
  private final QuoridorPlayer player2;

  // edge maps
  private Map<LineEndpoints, QuoridorEdge> endpointsToEdge = new HashMap<>(); // maps endpoints to actual edge
                                                                              // objects
  private Map<QuoridorEdge, List<QuoridorTile>> edgeToTiles = new HashMap<>(); // maps edges to the tiles that
                                                                               // they're a part of

  // constructor initializes board and players
  public QuoridorBoard(int rows, int cols, QuoridorPlayer player1, QuoridorPlayer player2) {
    super(rows, cols);
    if (rows != cols) {
      throw new IllegalArgumentException("Quoridor board must be square.");
    }
    this.player1 = player1;
    this.player2 = player2;

    int[] edgeOffsets = { 1, 10 };
    // populate the map of endpoints to edges
    for (int r = 0; r <= rows; r++) {
      for (int c = 0; c <= cols; c++) {
        for (int offset : edgeOffsets) {
          int p1 = r * 10 + c;
          int p2 = p1 + offset;
          LineEndpoints pointPair = new LineEndpoints(p1, p2);
          if (isValidEdge(pointPair)) {
            QuoridorEdge edge = new QuoridorEdge(pointPair);
            endpointsToEdge.put(pointPair, edge);
            LineEndpoints swappedPointPair = new LineEndpoints(p2, p1);
            endpointsToEdge.put(swappedPointPair, edge);
          }
        }
      }
    }
    // loop through all top left corners of boxes and create tiles to populate map
    // of edge to tile and the tile array
    tiles = new QuoridorTile[board_rows][board_cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        int topLeftVertex = r * 10 + c;
        QuoridorEdge[] tileEdges = new QuoridorEdge[4];
        for (int offsetIndex = 0; offsetIndex < verticesOffsets.length; offsetIndex++) {
          int p1 = topLeftVertex + verticesOffsets[offsetIndex];
          int p2 = topLeftVertex + verticesOffsets[(offsetIndex + 1) % verticesOffsets.length];
          LineEndpoints selectedEdgeEndpoints = new LineEndpoints(p1, p2);
          QuoridorEdge selectedEdge = endpointsToEdge.get(selectedEdgeEndpoints);
          tileEdges[offsetIndex] = selectedEdge;
        }
        QuoridorTile tile = new QuoridorTile(topLeftVertex, tileEdges);
        tiles[r][c] = tile;
        for (QuoridorEdge edge : tileEdges) {
          edgeToTiles.computeIfAbsent(edge, k -> new ArrayList<>()).add(tile); // makes a list if one doesn't exist
        }
      }
    }
  }

  // check if one of the players has reached the opposite end
  public boolean isSolved() {
    return (player1.getRow() == player1.getGoalRow()) || (player2.getRow() == player2.getGoalRow());
  }

  /**
   * checks if a tile point is within bounds
   * 
   * @return true if tile point is within the board dimensions
   */
  public boolean isWithinBounds(int x, int y) {
    return (x >= 0) && (x < board_rows) && (y >= 0) && (y < board_cols);
  }

  /**
   * checks if the input line endpoints (which contains two endpoints p1 and p2)
   * is within bounds
   * 
   * @param ends points p1 and p2 which form one line
   * @return true if points are adjacent and both ends are within bounds to form a
   *         valid edge
   */
  public boolean isValidEdge(LineEndpoints ends) {
    boolean pointsAreAdjacent = (Math.abs(ends.p1 - ends.p2) == 1 || Math.abs(ends.p1 - ends.p2) == 10);
    return pointsAreAdjacent && isEdgePointWithinBounds(ends.p1) && isEdgePointWithinBounds(ends.p2);
  }

  public boolean isEdgePointWithinBounds(int point) {
    int x = point % 10;
    int y = point / 10;
    return (x >= 0) && (x <= board_rows) && (y >= 0) && (y <= board_cols);
  }

  // just for testing when we force a win with "w"
  public void setBoardToSolvedState() {
    player1.setPosition(player1.getGoalRow(), player1.getCol());
  }

  /**
   * move the player if move is valid
   * 
   * @param mover current player that is moving
   * @param dr    change in row position
   * @param dc    change in column position
   * @return true if move is successful and false otherwise
   */
  // TODO check for walls and for jumps
  // TODO: either update this or input message so that inputs match as either new
  // coordinates or offset from old coordinates (can derive in either direction so
  // doesn't really matter -- just whatever is easiest for users)
  public boolean tryMove(QuoridorPlayer mover, int dr, int dc) {
    int nr = mover.getRow() + dr;
    int nc = mover.getCol() + dc;

    if (!isWithinBounds(nr, nc))
      return false;

    // can't move onto opponent
    QuoridorPlayer other = (mover == player1) ? player2 : player1;
    if (nr == other.getRow() && nc == other.getCol())
      return false;

    mover.setPosition(nr, nc);
    mover.incrementMoves();
    return true;
  }

  /**
   * place wall if valid
   * 
   * @param currPlayer the current player
   * @param r1         row for endpoint 1
   * @param c1         column for endpoint 1
   * @param r2         row for endpoint 2
   * @param c2         column for endpoint 2
   * @return true if successfully places wall -- false if not
   */
  public boolean tryPlaceWall(QuoridorPlayer currPlayer, int r1, int c1, int r2, int c2) {
    boolean hasWallsLeftToPlace = currPlayer.getWallsRemaining() > 0; // wall count check
    // TODO: implement legal wall location + BFS path + wall count checks -> place
    // wall (implement storage) + decrement wall count
    return false;
  }

  // toString
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
          QuoridorEdge edge = endpointsToEdge.get(new LineEndpoints(p1, p2));
          sb.append(edge.toString()); // TODO: add some colors here if edge was marked
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
          QuoridorEdge vEdge = endpointsToEdge.get(new LineEndpoints(p1, p2));
          sb.append(vEdge.toString()); // TODO: add some colors here if edge was marked
          // handle tile center
          if (c < board_cols) {
            boolean isPlayer1Here = (player1.getRow() == r && player1.getCol() == c);
            boolean isPlayer2Here = (player2.getRow() == r && player2.getCol() == c);

            if (isPlayer1Here) {
              sb.append(DotsAndBoxesOwnershipEnum.PLAYER1.getColor() + " 1" + DotsAndBoxesOwnershipEnum.getReset());
            } else if (isPlayer2Here) {
              sb.append(DotsAndBoxesOwnershipEnum.PLAYER2.getColor() + " 2" + DotsAndBoxesOwnershipEnum.getReset());
            } else {
              sb.append("  ");
            }
          }
        }
        sb.append("\n");
      }
    }

    // walls remaining stats
    sb.append("\n").append(player1.getPlayerName()).append(" walls: ").append(player1.getWallsRemaining()).append("\n")
        .append(player2.getPlayerName()).append(" walls: ").append(player2.getWallsRemaining()).append("\n");

    return sb.toString();
  }
}