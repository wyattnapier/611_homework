package Games.Quoridor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

import Games.Core.Board;
import Games.Core.AnsiColor;
import Games.Core.CoordPoint;
import Games.Core.LineEndpoints;
import Games.Enums.DotsAndBoxesOwnershipEnum;

/**
 * handles the board state for the quoridor game and the core logic
 */
public class QuoridorBoard extends Board {
  // set size of board and walls per player as constants
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
    resetPlayersToStart();

    // populate the map of endpoints to edges
    for (int r = 0; r <= rows; r++) {
      for (int c = 0; c <= cols; c++) {
        CoordPoint curr = new CoordPoint(r, c);
        CoordPoint horizNeighbor = new CoordPoint(r, c + 1);
        CoordPoint vertNeighbor = new CoordPoint(r + 1, c);
        LineEndpoints horizPair = new LineEndpoints(curr, horizNeighbor);
        LineEndpoints vertPair = new LineEndpoints(curr, vertNeighbor);

        for (LineEndpoints pair : new LineEndpoints[] { horizPair, vertPair }) {
          if (isValidEdge(pair)) {
            QuoridorEdge edge = new QuoridorEdge(pair);
            endpointsToEdge.put(pair, edge);
            // don't need to add swapped order pair because of the xor in the hash for
            // endpoints
          }
        }
      }
    }
    // loop through all top left corners of boxes and create tiles to populate map
    // of edge to tile and the tile array
    tiles = new QuoridorTile[board_rows][board_cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        // 4 corners of this tile: topLeft, topRight, bottomLeft, bottomRight
        CoordPoint topLeft = new CoordPoint(r, c);
        CoordPoint topRight = new CoordPoint(r, c + 1);
        CoordPoint bottomLeft = new CoordPoint(r + 1, c);
        CoordPoint bottomRight = new CoordPoint(r + 1, c + 1);
        CoordPoint[] corners = { topLeft, topRight, bottomRight, bottomLeft };
        QuoridorEdge[] tileEdges = new QuoridorEdge[4];

        for (int i = 0; i < corners.length; i++) {
          CoordPoint a = corners[i];
          CoordPoint b = corners[(i + 1) % corners.length];
          tileEdges[i] = endpointsToEdge.get(new LineEndpoints(a, b));
        }
        QuoridorTile tile = new QuoridorTile(topLeft, tileEdges);
        tiles[r][c] = tile;
        for (QuoridorEdge edge : tileEdges) {
          edgeToTiles.computeIfAbsent(edge, k -> new ArrayList<>()).add(tile); // makes a list if one doesn't exist
        }
      }
    }
  }

  /**
   * check if one of the players has reached the opposite end
   * 
   * @return returns true if board is solved
   */
  public boolean isSolved() {
    return (player1.getRow() == player1.getGoalRow()) || (player2.getRow() == player2.getGoalRow());
  }

  /**
   * checks if a tile point is within bounds
   * 
   * @return true if tile point is within the board dimensions
   */
  public boolean isTileWithinBounds(int x, int y) {
    return (x >= 0) && (x < board_rows) && (y >= 0) && (y < board_cols);
  }

  /**
   * checks if the input line endpoints (which contains two endpoints p1 and p2)
   * is within bounds (bounds are different for tile than end points)
   * 
   * @param ends points p1 and p2 which form one line
   * @return true if points are adjacent and both ends are within bounds to form a
   *         valid edge
   */
  public boolean isValidEdge(LineEndpoints ends) {
    boolean pointsAreAdjacent = ends.areEndpointsAdjacent();
    return pointsAreAdjacent && isEdgePointWithinBounds(ends.p1) && isEdgePointWithinBounds(ends.p2);
  }

  /**
   * checks if edge point is within bounds
   * 
   * @param point edgepoint like a vertex
   * @return true if point is within bounds of board for edges
   */
  public boolean isEdgePointWithinBounds(CoordPoint point) {
    int r = point.getRow();
    int c = point.getCol();
    return (0 <= r) && (r <= board_rows) && (0 <= c) && (c <= board_cols);
  }

  /**
   * set board to solved state when inputting secret win command "win"
   */
  public void setBoardToSolvedState() {
    player1.setPosition(player1.getGoalRow(), player1.getCol());
    player2.setPosition(player2.getGoalRow(), player2.getCol());
  }

  /**
   * resets the number of remaining walls for each player and put them back in
   * their starting position
   */
  public void resetPlayersToStart() {
    int startCol = board_cols / 2; // takes the floor to give correct index
    player1.setPosition(board_rows - 1 - player1.getGoalRow(), startCol);
    player2.setPosition(board_rows - 1 - player2.getGoalRow(), startCol);
    player1.setWallsRemaining(INITIAL_WALLS_PER_PLAYER);
    player2.setWallsRemaining(INITIAL_WALLS_PER_PLAYER);
  }

  /**
   * moves given player to new coordinate calculated from given offset
   * 
   * @param mover current player
   * @param dr    change in row
   * @param dc    change in col
   * @return true if successful and false otherwise
   */
  public boolean tryMove(QuoridorPlayer mover, int dr, int dc) {
    List<CoordPoint> validMoves = getValidMoves(mover);
    for (CoordPoint move : validMoves) {
      if (move.getRow() == dr && move.getCol() == dc) {
        mover.setPosition(mover.getRow() + dr, mover.getCol() + dc);
        return true;
      }
    }
    return false;
  }

  /**
   * returns all valid moves for a given player as a list of [dr, dc] pairs
   *
   * @param mover the player whose valid moves we want
   * @return list of valid [dr, dc] offset pairs
   */
  public List<CoordPoint> getValidMoves(QuoridorPlayer mover) {
    return getValidMoves(mover, mover.getRow(), mover.getCol(), false);
  }

  /**
   * returns all valid moves for a given player as a list of [dr, dc] pairs
   *
   * @param mover          the player whose valid moves we want
   * @param currRow        the current row of player
   * @param currCol        the current column of player
   * @param ignoreOpponent should you ignore the opponent's when generating valid
   *                       moves
   * @return list of valid [dr, dc] offset pairs
   */
  public List<CoordPoint> getValidMoves(QuoridorPlayer mover, int currRow, int currCol, boolean ignoreOpponent) {
    List<CoordPoint> validMoves = new ArrayList<>();
    int cr = currRow;
    int cc = currCol;

    QuoridorPlayer other = (mover == player1) ? player2 : player1;
    int or = other.getRow();
    int oc = other.getCol();

    CoordPoint[] cardinalDirections = { new CoordPoint(-1, 0), new CoordPoint(1, 0), new CoordPoint(0, -1),
        new CoordPoint(0, 1) };

    for (CoordPoint dir : cardinalDirections) {
      int dr = dir.getRow();
      int dc = dir.getCol();
      int nr = cr + dr;
      int nc = cc + dc;

      if (!isTileWithinBounds(nr, nc) || isWallBetween(cr, cc, nr, nc))
        continue;

      // opponent is in the way — check for jumps
      if (nr == or && nc == oc && !ignoreOpponent) {
        int jumpR = nr + dr;
        int jumpC = nc + dc;

        // straight jump
        if (isTileWithinBounds(jumpR, jumpC) && !isWallBetween(nr, nc, jumpR, jumpC)) {
          validMoves.add(new CoordPoint(jumpR - cr, jumpC - cc));
          continue;
        }

        // diagonal jumps (straight blocked)
        int[] perpDr = (dr != 0) ? new int[] { 0, 0 } : new int[] { -1, 1 };
        int[] perpDc = (dc != 0) ? new int[] { -1, 1 } : new int[] { 0, 0 };

        for (int i = 0; i < 2; i++) {
          int diagR = nr + perpDr[i];
          int diagC = nc + perpDc[i];
          if (isTileWithinBounds(diagR, diagC) && !isWallBetween(nr, nc, diagR, diagC)) {
            validMoves.add(new CoordPoint(diagR - cr, diagC - cc));
          }
        }
        continue;
      }
      // normal move
      validMoves.add(new CoordPoint(dr, dc));
    }
    return validMoves;
  }

  /**
   * checks if there is a wall on the shared edge between two adjacent tiles
   *
   * @param r1 row of first tile
   * @param c1 col of first tile
   * @param r2 row of second tile
   * @param c2 col of second tile
   * @return true if a wall exists between them
   */
  private boolean isWallBetween(int r1, int c1, int r2, int c2) {
    QuoridorTile tile = tiles[r1][c1];
    QuoridorEdge sharedEdge;

    if (r2 == r1 - 1)
      sharedEdge = tile.getTopEdge();
    else if (r2 == r1 + 1)
      sharedEdge = tile.getBottomEdge();
    else if (c2 == c1 - 1)
      sharedEdge = tile.getLeftEdge();
    else
      sharedEdge = tile.getRightEdge();

    return sharedEdge != null && sharedEdge.isWall();
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
    if (currPlayer.getWallsRemaining() <= 0) {
      System.out.println("You cannot place any more walls");
      return false;
    }
    CoordPoint point1 = new CoordPoint(r1, c1);
    CoordPoint point2 = new CoordPoint(r2, c2);
    if (!(isEdgePointWithinBounds(point1) && isEdgePointWithinBounds(point2))) {
      System.out.println("An edge endpoint is out of bounds.");
      return false;
    }
    // horizontally aligned
    CoordPoint midPoint;
    if (r1 == r2) {
      if (Math.abs(c1 - c2) != 2) {
        System.out.println("Wall must be length 2");
        return false;
      }
      int midpointCol = Math.max(c1, c2) - 1;
      midPoint = new CoordPoint(r1, midpointCol);
    }
    // vertically aligned
    else if (c1 == c2) {
      if (Math.abs(r1 - r2) != 2) {
        System.out.println("Wall must be length 2");
        return false;
      }
      int midpointRow = Math.max(r1, r2) - 1;
      midPoint = new CoordPoint(midpointRow, c1);
    } else {
      // must be aligned in either rows or columns
      System.out.println("Walls must be horizontal or vertical and length 2.");
      return false;
    }
    // mark the edges and check for overlap (won't implement change if there is
    // overlap)
    QuoridorEdge edge1 = endpointsToEdge.get(new LineEndpoints(point1, midPoint));
    QuoridorEdge edge2 = endpointsToEdge.get(new LineEndpoints(point2, midPoint));
    if (!edge1.setIsWall(true)) {
      System.out.println("New walls cannot overlap with existing walls");
      return false;
    }
    if (!edge2.setIsWall(true)) {
      edge1.setIsWall(false); // roll back the change to edge1
      System.out.println("New walls cannot overlap with existing walls");
      return false;
    }

    // run BFS check for path existence and if it fails the unmark the edges
    if (!pathExists()) {
      edge1.setIsWall(false);
      edge2.setIsWall(false);
      System.out.println("You cannot completely trap a player with walls.");
      return false;
    }

    currPlayer.useWall();
    return true;
  }

  /**
   * checks if a path exists to the goal row for both players
   * 
   * @return true if path exists else false
   */
  private boolean pathExists() {
    return pathExistsForPlayer(player1) && pathExistsForPlayer(player2);
  }

  /**
   * checks if a path exists to the goal row for a particular player through BFS
   * 
   * @param player current player we're checking for
   * @return true if path exists false otherwise
   */
  private boolean pathExistsForPlayer(QuoridorPlayer player) {
    CoordPoint source = new CoordPoint(player.getRow(), player.getCol()); // won't be goal row because otherwise game is
                                                                          // already over
    int goalRow = player.getGoalRow();
    boolean isFirstMove = true;

    Set<CoordPoint> visitedCoords = new HashSet<>();
    Queue<CoordPoint> q = new LinkedList<>();
    q.add(source);

    while (!q.isEmpty()) {
      CoordPoint currPoint = q.remove();
      if (visitedCoords.contains(currPoint))
        continue;
      visitedCoords.add(currPoint);

      List<CoordPoint> validMovesOffsets = getValidMoves(player, currPoint.getRow(), currPoint.getCol(), !isFirstMove);
      for (CoordPoint moveOffset : validMovesOffsets) {
        CoordPoint neighbor = moveOffset.plus(currPoint);
        if (neighbor.getRow() == goalRow)
          return true;
        if (!visitedCoords.contains(neighbor))
          q.add(neighbor);
      }

      isFirstMove = false;
    }
    return false;
  }

  // toString
  public String toString() {
    return toString(null);
  }

  public String toString(QuoridorPlayer focusPlayer) {
    StringBuilder sb = new StringBuilder();
    Set<CoordPoint> highlightedMoves = new HashSet<>();

    if (focusPlayer != null) {
      for (CoordPoint moveOffset : getValidMoves(focusPlayer)) {
        highlightedMoves.add(moveOffset.plus(new CoordPoint(focusPlayer.getRow(), focusPlayer.getCol())));
      }
    }

    // walls remaining stats
    sb.append("\n").append(player1.getPlayerName()).append(" walls: ").append(player1.getWallsRemaining()).append("\n")
        .append(player2.getPlayerName()).append(" walls: ").append(player2.getWallsRemaining()).append("\n");

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
          CoordPoint p1 = new CoordPoint(r, c);
          CoordPoint p2 = new CoordPoint(r, c + 1);
          QuoridorEdge edge = endpointsToEdge.get(new LineEndpoints(p1, p2));
          if (edge.isWall()) {
            sb.append(AnsiColor.wrap(AnsiColor.WALL, edge.toString()));
          } else {
            sb.append(edge.toString());
          }
        }
      }
      sb.append("\n");

      // vertical edges and tile centers
      if (r < board_rows) {
        sb.append("     ");
        for (int c = 0; c <= board_cols; c++) {
          // Handle Vertical Edge
          CoordPoint p1 = new CoordPoint(r, c);
          CoordPoint p2 = new CoordPoint(r + 1, c);
          QuoridorEdge vEdge = endpointsToEdge.get(new LineEndpoints(p1, p2));
          if (vEdge.isWall()) {
            sb.append(AnsiColor.wrap(AnsiColor.WALL, vEdge.toString()));
          } else {
            sb.append(vEdge.toString());
          }
          // handle tile center
          if (c < board_cols) {
            boolean isPlayer1Here = (player1.getRow() == r && player1.getCol() == c);
            boolean isPlayer2Here = (player2.getRow() == r && player2.getCol() == c);

            if (isPlayer1Here) {
              sb.append(AnsiColor.wrap(DotsAndBoxesOwnershipEnum.PLAYER1.getColor(), " 1"));
            } else if (isPlayer2Here) {
              sb.append(AnsiColor.wrap(DotsAndBoxesOwnershipEnum.PLAYER2.getColor(), " 2"));
            } else if (highlightedMoves.contains(new CoordPoint(r, c))) {
              sb.append(AnsiColor.wrap(AnsiColor.HIGHLIGHT, " +"));
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