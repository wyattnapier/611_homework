public class EmptyTile extends Tile {

  /**
   * Constructor for EmptyTile
   * 
   * @param val        value of the tile (should be m * n - 1)
   * @param x_pos      current x position of the tile
   * @param y_pos      current y position of the tile
   * @param dest_x_pos destination x position of the tile when game is solved
   *                   (bottom right corner)
   * @param dest_y_pos destination y position of the tile when game is solved
   *                   (bottom right corner)
   */
  public EmptyTile(int val, int x_pos, int y_pos, int dest_x_pos, int dest_y_pos) {
    super(val, x_pos, y_pos, dest_x_pos, dest_y_pos);
  }

  @Override
  public String toString() {
    return " "; // just a space because is empty tile
  }
}
