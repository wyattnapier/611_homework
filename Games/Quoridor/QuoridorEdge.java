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
  public void addWallToEdge() {
    edgeHasWall = true;
  }

  /**
   * 
   * @return true if this edge has a wall else false
   */
  public boolean getEdgeHasWall() {
    return edgeHasWall;
  }
}
