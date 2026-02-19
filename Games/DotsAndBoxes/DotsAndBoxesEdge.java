package Games.DotsAndBoxes;

import Games.Core.Endpoints;

public class DotsAndBoxesEdge {
  private Endpoints edgeEndpoints; // two endpoint of a line
  private DotsAndBoxesOwnership edgeOwner = DotsAndBoxesOwnership.NOBODY;

  public DotsAndBoxesEdge(Endpoints ends) {
    edgeEndpoints = ends;
  }

  /**
   * @param status represents edge owner or 0 if neither
   */
  public void setEdgeOwner(DotsAndBoxesOwnership status) {
    edgeOwner = status;
  }

  /**
   * @return int representing the edge owner (player 1 or player 2) or 0 if
   *         neither owns it
   */
  public DotsAndBoxesOwnership getEdgeOwner() {
    return edgeOwner;
  }

  public Boolean isEdgeDrawn() {
    return edgeOwner != DotsAndBoxesOwnership.NOBODY;
  }

  public Endpoints getEdgeEndpoints() {
    return edgeEndpoints;
  }
}
