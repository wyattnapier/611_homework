package Games.Quoridor;

import Games.Core.Board;

// this assumes some sort of QuoridorPlayer and QuoridorTile classes
// TODO implement QuoridorPlayer and QuoridorTile classes

public class QuoridorBoard extends Board {
  // set size of board and walls per player as constants
  public final int DEFAULT_SIZE = 9;
  public final int INITIAL_WALLS_PER_PLAYER = 10;

  // board of tiles and players
  private final QuoridorTile[][] tiles;
  private final QuoridorPlayer p1;
  private final QuoridorPlayer p2;

  // constructor initializes board and players
  public QuoridorBoard(int rows, int cols, QuoridorPlayer p1, QuoridorPlayer p2) {
    super(rows, cols);
    if (rows != cols) {
      throw new IllegalArgumentException("Quoridor board must be square.");
    }
    tiles = new QuoridorTile[rows][cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        tiles[r][c] = new QuoridorTile(r, c);
      }
    }
    this.p1 = p1;
    this.p2 = p2;
  }

  // check if one of the players has reached the opposite end
  public boolean isSolved() {
    return (p1.getRow() == p1.getGoalRow()) || (p2.getRow() == p2.getGoalRow());
  }

  public boolean isWithinBounds(int x, int y) {
    return (x >= 0) && (x < board_rows) && (y >= 0) && (y < board_cols);
  }

  // just for testing when we force a win with "w"
  public void setBoardToSolvedState() {
    p1.setPosition(p1.getGoalRow(), p1.getCol());
  }

  // TODO check for walls and for jumps
  // move the player if it's valid
  public boolean tryMove(QuoridorPlayer mover, int dr, int dc) {
    int nr = mover.getRow() + dr;
    int nc = mover.getCol() + dc;

    if (!isWithinBounds(nr, nc))
      return false;

    // can't move onto opponent
    QuoridorPlayer other;
    if (mover == p1)
      other = p2;
    else
      other = p1;
    if (nr == other.getRow() && nc == other.getCol())
      return false;

    mover.setPosition(nr, nc);
    mover.incrementMoves();
    return true;
  }

  // place wall if valid
  public boolean tryPlaceWall(QuoridorPlayer mover, int row, int col, char orientation) {
    // TODO: implement legal wall location + BFS path + wall count checks -> place
    // wall (implement storage) + decrement wall count
    return false;
  }

  // toString
  public String toString() {
    StringBuilder sb = new StringBuilder();

    // simple grid print with player markers
    for (int r = 0; r < board_rows; r++) {
      for (int c = 0; c < board_cols; c++) {
        if (r == p1.getRow() && c == p1.getCol())
          sb.append("1 ");
        else if (r == p2.getRow() && c == p2.getCol())
          sb.append("2 ");
        else
          sb.append(tiles[r][c].toString()).append(" ");
      }
      sb.append("\n");
    }

    sb.append("\n")
        .append(p1.getPlayerName()).append(" walls: ").append(p1.getWallsRemaining()).append("\n")
        .append(p2.getPlayerName()).append(" walls: ").append(p2.getWallsRemaining()).append("\n");

    return sb.toString();
  }
}