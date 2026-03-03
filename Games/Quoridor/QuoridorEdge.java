package Games.Quoridor;

import Games.Core.Edge;
import Games.Core.LineEndpoints;

public class QuoridorEdge extends Edge {
  boolean edgeHasWall;

  public QuoridorEdge(LineEndpoints ends) {
    super(ends);
    edgeHasWall = false;
  }

  /**
   * adds a wall to an edge
   */
  public boolean setIsWall(boolean markIsWall) {
    if (isWall() == markIsWall)
      return false;
    edgeHasWall = markIsWall;
    return true;
  }

  /**
   * 
   * @return true if this edge has a wall else false
   */
  public boolean isWall() {
    return edgeHasWall;
  }
}
