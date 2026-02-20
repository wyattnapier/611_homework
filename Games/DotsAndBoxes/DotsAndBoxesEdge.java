package Games.DotsAndBoxes;

import Games.Core.Endpoints;
import Games.Enums.DotsAndBoxesOwnership;

public class DotsAndBoxesEdge {
  private Endpoints edgeEndpoints; // two endpoint of a line
  private DotsAndBoxesOwnership edgeOwner = DotsAndBoxesOwnership.NOBODY;

  public DotsAndBoxesEdge(Endpoints ends) {
    edgeEndpoints = ends;
  }

  /**
   * @param owner person who drew the edge
   */
  public void setEdgeOwner(DotsAndBoxesOwnership owner) {
    edgeOwner = owner;
  }

  /**
   * @return int representing the edge owner (player 1 or player 2) or 0 if
   *         neither owns it
   */
  public DotsAndBoxesOwnership getEdgeOwner() {
    return edgeOwner;
  }

  public Boolean edgeHasOwner() {
    return edgeOwner != DotsAndBoxesOwnership.NOBODY;
  }

  public Endpoints getEdgeEndpoints() {
    return edgeEndpoints;
  }

  @Override
  public String toString() {
    int p1 = edgeEndpoints.p1;
    int p2 = edgeEndpoints.p2;
    String output = p1 + ", " + p2 + " is owned by " + edgeOwner;
    return output;
  }
}
