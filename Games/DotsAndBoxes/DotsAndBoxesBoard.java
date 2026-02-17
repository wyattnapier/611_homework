package Games.DotsAndBoxes;

import Games.Core.Board;
import Games.Core.Tile;

public class DotsAndBoxesBoard extends Board {
  private Tile[][] tiles;

  public DotsAndBoxesBoard(int rows, int cols) {
    super(rows, cols);
    board_rows = rows;
    board_cols = cols;
    tiles = new Tile[board_rows][board_cols]; // TODO: initialize board with specific tile type based on board type
                                              // (e.g.
    // SlidingPuzzleBoard should initialize with SlidingPuzzleTiles)
  }

  public boolean isSolved() {
    return false; // TODO: implement when Dots and Boxes is implemented
  }

  public void setBoardToSolvedState() {
    return; // TODO: implement when Dots and Boxes is implemented
  }

  public String toString() {
    return "NOTE: still need to implement Dots and Boxes board representation"; // TODO: implement when Dots and Boxes
                                                                                // is implemented
  }
}
