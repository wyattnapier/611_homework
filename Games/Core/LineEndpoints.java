package Games.Core;

import java.util.Objects;

/**
 * This class is for endpoint objects. In the dots and boxes game, these are
 * treated as endpoint pairs for an edge. Thus, p1 is one point of the edge and
 * p2 is the other point. This can be done with two ints because the board
 * doesn't exceed a size of 9x9 so the largest point would be 9,9 represented as
 * the int 99
 */
public class LineEndpoints {
  // points calculated as (e.g. r*10 + c)
  public final int p1;
  public final int p2;

  public LineEndpoints(int endpoint1, int endpoint2) {
    p1 = endpoint1;
    p2 = endpoint2;
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
    return (p1 == c.p1 && p2 == c.p2) || (p1 == c.p2 && p2 == c.p1); // order doesn't matter for points
  }

  /**
   * overrides hashcode method for this specific object so it is calculated using
   * the relevant coordinates of p1 and p2. If using as a key in a hashmap, still
   * need to hash another with points reversed for full commutativity
   */
  public int hashCode() {
    return Objects.hash(p1, p2);
  }
}
