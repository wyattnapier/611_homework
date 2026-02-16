package Games.Core;

public class Tile {
    private int value; // value of the tile
    private int x, y; // position of the tile
    private int dest_x, dest_y; // destination position of the tile when game is solved

    /**
     * Constructor for Tile
     * 
     * @param val        value of the tile
     * @param x_pos      current x position of the tile
     * @param y_pos      current y position of the tile
     * @param dest_x_pos destination x position of the tile when game is solved
     * @param dest_y_pos destination y position of the tile when game is solved
     */
    public Tile(int val, int x_pos, int y_pos, int dest_x_pos, int dest_y_pos) {
        value = val;
        x = x_pos;
        y = y_pos;
        dest_x = dest_x_pos;
        dest_y = dest_y_pos;
    }

    /**
     * Update the current position of the tile
     * 
     * @param newX new x position
     * @param newY new y position
     */
    public void updatePosition(int newX, int newY) {
        x = newX;
        y = newY;
    }

    /**
     * Check if the tile is in its correct final position
     * 
     * @return true if in correct position, false otherwise
     */
    public boolean isCorrectPosition() {
        return x == dest_x && y == dest_y;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
/**
 * for the dots and boxes game we want a tile to represent a single square on
 * the grid
 * that means we will need booleans for each of the four sides to represent
 * whether or not they have been claimed by a player, and an integer to
 * represent which player has claimed the tile (if any)
 * we will also want a method to check if the tile has been completed (all four
 * sides claimed) and a method to claim a side of the tile for a player
 * we will also need to keep track of the position of the tile on the grid, so
 * we can determine which tiles are adjacent to it and update the game state
 * accordingly when a player claims a side
 * 
 * will still have a board of tiles but each tile will be more abstracted and
 * will extend to have more game specific information
 */