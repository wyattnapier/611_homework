package Games.Core;

/**
 * This class is for endpoint objects. In the dots and boxes game, these are
 * treated as endpoint pairs for an edge. Thus, p1 is one point of the edge and
 * p2 is the other point. This can be done with two ints because the board
 * doesn't exceed a size of 9x9 so the largest point would be 9,9 represented as
 * the int 99
 */
public class LineEndpoints {
  // points calculated as (e.g. r*10 + c)
  public final CoordPoint p1;
  public final CoordPoint p2;

  public LineEndpoints(CoordPoint endpoint1, CoordPoint endpoint2) {
    p1 = endpoint1;
    p2 = endpoint2;
  }

  public boolean areEndpointsAdjacent() {
    CoordPoint sub = p1.minus(p2);
    boolean horizontallyAdjacent = sub.getRow() == 0 && Math.abs(sub.getCol()) == 1;
    boolean verticallyAdjacent = Math.abs(sub.getRow()) == 1 && sub.getCol() == 0;
    return horizontallyAdjacent || verticallyAdjacent;
  }

  /**
   * equals method for this specific object
   * order of points doesn't matter because a line from 00 to 01 is the same as a
   * line from 01 to 00 since it isn't directed
   */
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof LineEndpoints))
      return false;
    LineEndpoints c = (LineEndpoints) o;
    return (p1.equals(c.p1) && p2.equals(c.p2)) || (p1.equals(c.p2) && p2.equals(c.p1));
  }

  /**
   * overrides hashcode method for this specific object so it is calculated using
   * the relevant coordinates of p1 and p2. If using as a key in a hashmap, still
   * need to hash another with points reversed for full commutativity
   * using an XOR the order doesn't matter
   */
  public int hashCode() {
    // return Objects.hash(p1, p2);
    return p1.hashCode() ^ p2.hashCode(); // XOR is commutative, so order doesn't matter
  }

  public String toString() {
    return "(" + p1 + ", " + p2 + ")";
  }
}
