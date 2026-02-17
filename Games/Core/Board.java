package Games.Core;

public abstract class Board {
    protected int m, n; // dimensions of the board
    // TODO: consider adjusting tiles type to be more specific to the game type?
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
        m = rows;
        n = cols;
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
    public boolean isWithinBounds(int x, int y) {
        return (x >= 0 && x < this.m && y >= 0 && y < this.n);
    }

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
