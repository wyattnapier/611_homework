package Games.SlidingPuzzle;

import java.util.*;
import Games.Core.Board;

public class SlidingPuzzleBoard extends Board {

  private int emptyTileX, emptyTileY; // coordinates of empty tile
  private SlidingPuzzleTile[][] tiles;

  /**
   * Constructor for Board
   * 
   * @param rows number of rows for the board
   * @param cols number of columns for the board
   */
  public SlidingPuzzleBoard(int rows, int cols) {
    super(rows, cols);
    // error handling for invalid board dimensions already done in Game when
    // prompting user for dimensions, but adding here for extra safety
    if (rows < 1 || cols < 1 || rows > 9 || cols > 9) {
      throw new IllegalArgumentException("Board dimensions must be 1 <= rows, cols <= 9");
    }
    tiles = new SlidingPuzzleTile[rows][cols];

    setBoardToSolvedState();
    shuffleBoardWithRandomMoves();
  }

  /**
   * Shuffle the board by making random valid moves from the solved state
   * This guarantees the puzzle is solvable
   */
  private void shuffleBoardWithRandomMoves() {
    Random random = new Random();
    int numMoves = 100 + random.nextInt(50); // 100-150 random moves

    for (int i = 0; i < numMoves; i++) {
      makeRandomValidMove(random);
    }
  }

  /**
   * Make a random valid move (swap empty tile with adjacent tile)
   */
  private void makeRandomValidMove(Random random) {
    ArrayList<int[]> validMoves = new ArrayList<>();

    // Find all valid adjacent positions
    for (int i = 0; i < ADJACENT_OFFSETS.length; i++) {
      int adjX = emptyTileX + ADJACENT_OFFSETS[i][0];
      int adjY = emptyTileY + ADJACENT_OFFSETS[i][1];

      if (isWithinBounds(adjX, adjY)) {
        int[] coords = { adjX, adjY };
        validMoves.add(coords);
      }
    }

    // Pick and execute a random valid move
    if (!validMoves.isEmpty()) {
      int[] move = validMoves.get(random.nextInt(validMoves.size()));
      performShufflingTileSwap(emptyTileX, emptyTileY, move[0], move[1]);
    }
  }

  /**
   * Perform a swap between two tiles and update their positions
   * Only used internally during shuffling
   */
  private void performShufflingTileSwap(int x1, int y1, int x2, int y2) {
    SlidingPuzzleTile temp = tiles[x1][y1];
    tiles[x1][y1] = tiles[x2][y2];
    tiles[x2][y2] = temp;

    // Update positions of the tiles
    tiles[x1][y1].updateTilePosition(x1, y1);
    tiles[x2][y2].updateTilePosition(x2, y2);

    // Update empty tile position
    setEmptyTileCoordinates(x2, y2);
  }

  /**
   * Calculate the target x position for a tile value
   * 
   * @param tileValue
   * @return target x position
   */
  private int calculateTileEndX(int tileValue) {
    return (tileValue - 1) / n;
  }

  /**
   * Calculate the target y position for a tile value
   * 
   * @param tileValue
   * @return target y position
   */
  private int calculateTileEndY(int tileValue) {
    return (tileValue - 1) % n;
  }

  /**
   * Check if the board is in a solved state
   * 
   * @return true if the board is solved, false otherwise
   */
  public boolean isSolved() {
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (!tiles[i][j].isComplete()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Check if given coordinates are within board bounds
   * 
   * @param x the x index
   * @param y the y index
   * @return true if within bounds, false otherwise
   */
  public boolean isWithinBounds(int x, int y) {
    return (x >= 0 && x < this.m && y >= 0 && y < this.n);
  }

  /**
   * Set the coordinates of the empty tile
   * 
   * @param newX the new x index for the empty tile
   * @param newY the new y index for the empty tile
   */
  public void setEmptyTileCoordinates(int newX, int newY) {
    if (!isWithinBounds(newX, newY)) {
      throw new IllegalArgumentException("Empty tile coordinates must be within board bounds");
    }
    emptyTileX = newX;
    emptyTileY = newY;
  }

  /**
   * Swap the selected tile with the empty tile if adjacent
   * used when player makes a move
   * 
   * @param selectedTileValue the value of the tile to swap
   * @return true if swap successful, false otherwise
   */
  public boolean swapTile(String selectedTileValue) {
    // swap selectedTile into empty tile slot
    int offsetIndex = findSwapOffsetIndex(selectedTileValue);
    if (offsetIndex == -1) {
      return false;
    }

    int adjacentX = emptyTileX + ADJACENT_OFFSETS[offsetIndex][0];
    int adjacentY = emptyTileY + ADJACENT_OFFSETS[offsetIndex][1];

    SlidingPuzzleTile selectedTile = tiles[adjacentX][adjacentY];
    tiles[emptyTileX][emptyTileY] = selectedTile;
    selectedTile.updateTilePosition(emptyTileX, emptyTileY);
    tiles[adjacentX][adjacentY] = new SlidingPuzzleEmptyTile(m * n, adjacentX, adjacentY, m - 1, n - 1);
    setEmptyTileCoordinates(adjacentX, adjacentY);
    return true;
  }

  /**
   * Find the offset index for the tile to swap with the empty tile
   * 
   * @param selectedTileValue the value of the tile to swap
   * @return the offset index if found, -1 otherwise
   */
  private int findSwapOffsetIndex(String selectedTileValue) {
    // Check if the selected tile is adjacent to the empty tile
    for (int i = 0; i < ADJACENT_OFFSETS.length; i++) {
      int[] offset = ADJACENT_OFFSETS[i];
      int searchX = emptyTileX + offset[0];
      int searchY = emptyTileY + offset[1];
      if (isWithinBounds(searchX, searchY)) {
        SlidingPuzzleTile adjacentTile = tiles[searchX][searchY];
        if (adjacentTile.toString().equals(selectedTileValue)) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * Set the board to the solved state for initialization or testing
   */
  public void setBoardToSolvedState() {
    int tileValue = 1;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        int dest_x = calculateTileEndX(tileValue);
        int dest_y = calculateTileEndY(tileValue);
        if (tileValue == m * n) {
          tiles[i][j] = new SlidingPuzzleEmptyTile(tileValue, i, j, dest_x, dest_y);
          setEmptyTileCoordinates(i, j);
        } else {
          tiles[i][j] = new SlidingPuzzleTile(tileValue, i, j, dest_x, dest_y);
        }
        tileValue++;
      }
    }
  }

  @Override
  public String toString() {
    String spacerChunk = (m * n > 10) ? "+----" : "+---";
    String s = "";
    String horizontalSpacer = "";
    for (int i = 0; i < n; i++) {
      horizontalSpacer += spacerChunk;
    }
    horizontalSpacer += "+\n";
    for (int i = 0; i < m; i++) {
      s += horizontalSpacer;
      for (int j = 0; j < n; j++) {
        String tileValue = tiles[i][j].toString();
        if (m * n > 10 && tileValue.length() == 1) {
          tileValue = " " + tileValue; // add extra space for alignment
        }
        s += "| " + tileValue + " ";
      }
      s += "|\n";
    }
    s += horizontalSpacer;
    return s;
  }
}
