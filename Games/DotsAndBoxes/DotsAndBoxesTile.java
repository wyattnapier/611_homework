package Games.DotsAndBoxes;

import Games.Core.Tile;

public class DotsAndBoxesTile implements Tile {
  private DotsAndBoxesOwnership owner;
  private int[] vertices; // list of corners in square starting top left and going clockwise
  private DotsAndBoxesEdge[] edges; // order edge list as up, right, left, right
  private int[] verticesOffsets = { 0, 10, 11, 1 }; // can use single numbers since max board is 9x9

  /**
   * @param topLeftCornerValue is the location of the top left corner vertex
   * @param edges              is a list of the references to the edges for the
   *                           tile
   */
  public DotsAndBoxesTile(int topLeftCornerValue, DotsAndBoxesEdge[] edges) {
    owner = DotsAndBoxesOwnership.NOBODY;
    vertices = new int[verticesOffsets.length];
    this.edges = edges;
    for (int i = 0; i < verticesOffsets.length; i++) {
      vertices[i] = topLeftCornerValue + verticesOffsets[i];
    }
  }

  public DotsAndBoxesOwnership getTileOwner() {
    return owner;
  }

  /**
   * @return true if square is complete (has all 4 edges drawn)
   */
  public boolean isComplete() {
    return owner != DotsAndBoxesOwnership.NOBODY;
  }

  /**
   * set a box to be complete if all edges in it are complete after marking an
   * edge
   * 
   * @param playerThatAddedLastEdge enum value for the player that most recently
   *                                marked an edge (player1 or player2)
   * @return true if input player just completed it and false otherwise
   */
  public Boolean setIsComplete(DotsAndBoxesOwnership playerThatAddedLastEdge) {
    if (isComplete())
      return false; // don't change ownership
    for (DotsAndBoxesEdge edge : edges) {
      if (!edge.edgeHasOwner()) {
        return false; // owner is still nobody, no need to change
      }
    }
    owner = playerThatAddedLastEdge;
    return true;
  }

  public String toString() {
    switch (owner) {
      case PLAYER1:
        return "1";
      case PLAYER2:
        return "2";
      default:
        return "";
    }
  }
}