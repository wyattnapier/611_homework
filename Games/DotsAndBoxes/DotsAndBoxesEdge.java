package Games.DotsAndBoxes;

import Games.Core.Endpoints;

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
}
