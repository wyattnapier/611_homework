package Games.DotsAndBoxes;

import Games.Core.LineEndpoints;
import Games.Core.Edge;
import Games.Enums.DotsAndBoxesOwnershipEnum;

/**
 * This class is an edge for the dots and boxes game. Each edge will have
 * endpoints and an owner.
 */
public class DotsAndBoxesEdge extends Edge {
  private DotsAndBoxesOwnershipEnum edgeOwner = DotsAndBoxesOwnershipEnum.NOBODY;

  public DotsAndBoxesEdge(LineEndpoints ends) {
    super(ends);
  }

  /**
   * @param owner person who drew the edge
   */
  public void setEdgeOwner(DotsAndBoxesOwnershipEnum owner) {
    edgeOwner = owner;
  }

  /**
   * @return int representing the edge owner (player 1 or player 2) or 0 if
   *         neither owns it
   */
  public DotsAndBoxesOwnershipEnum getEdgeOwner() {
    return edgeOwner;
  }

  /**
   * 
   * @return true if edge has owner
   */
  public Boolean edgeHasOwner() {
    return edgeOwner != DotsAndBoxesOwnershipEnum.NOBODY;
  }

  /**
   * toString method used within the DotsAndBoxedBoard toString method
   * 
   * @return string representation of edge with color
   */
  @Override
  public String toString() {
    String line = Math.abs(edgeEndpoints.p1 - edgeEndpoints.p2) == 10 ? "|" : "──";
    String output = edgeOwner.getColor() + line + DotsAndBoxesOwnershipEnum.getReset();
    return output;
  }

  /**
   * to string method used for debugging
   * 
   * @return String of information about edge
   */
  public String debuggingToString() {
    int p1 = edgeEndpoints.p1;
    int p2 = edgeEndpoints.p2;
    String output = p1 + ", " + p2 + " is owned by " + edgeOwner;
    return output;
  }
}
