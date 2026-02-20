package Games.Core;

/**
 * This class is used to established some shared instance variables for boards
 * and build a template for important functions that all boards must implement
 */
public abstract class Board {
    // board_rows is m and board_cols is n
    protected int board_rows, board_cols; // dimensions of the board
    protected static final int[][] ADJACENT_OFFSETS = {
            { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } }; // up, right, down, left

    /**
     * Constructor for Board
     * 
     * @param rows number of rows for the board
     * @param cols number of columns for the board
     */
    public Board(int rows, int cols) {
        // error handling for invalid board dimensions already done in Game when
        // prompting user for dimensions
        board_rows = rows;
        board_cols = cols;
    }

    /**
     * Check if the board is in a solved state
     * 
     * @return true if the board is solved, false otherwise
     */
    public abstract boolean isSolved();

    /**
     * Check if given coordinates are within board bounds
     * 
     * @param x the x index
     * @param y the y index
     * @return true if within bounds, false otherwise
     */
    public abstract boolean isWithinBounds(int x, int y);

    /**
     * Set the board to the solved state for initialization or testing
     */
    public abstract void setBoardToSolvedState();

    /**
     * must override toString for each board type
     * 
     * @return string representation of the board
     */
    public abstract String toString();
}
