package Games.Core;

import java.util.Objects;

public class CoordPoint {
  private int row, col;

  public CoordPoint(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public CoordPoint plus(CoordPoint c) {
    return new CoordPoint(this.getRow() + c.getRow(), this.getCol() + c.getCol());
  }

  /**
   * subtracts input CoordPoint from current CoordPoint
   * 
   * @param c CoordPoint that is subtrahend
   * @return this - c
   */
  public CoordPoint minus(CoordPoint c) {
    return new CoordPoint(this.getRow() - c.getRow(), this.getCol() - c.getCol());
  }

  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof CoordPoint))
      return false;
    CoordPoint c = (CoordPoint) o;
    return row == c.row && col == c.col;
  }

  public String toString() {
    return "(" + row + ", " + col + ")";
  }

  public int hashCode() {
    return Objects.hash(row, col);
  }
}
