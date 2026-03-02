package Games.Core;

public abstract class Edge {
  protected LineEndpoints edgeEndpoints; // two endpoint of a line

  public Edge(LineEndpoints ends) {
    edgeEndpoints = ends;
  }

  /**
   * 
   * @return endpoints of the edge as a LineEndpoints object
   */
  public LineEndpoints getEdgeEndpoints() {
    return edgeEndpoints;
  }

  /**
   * @return string
   */
  public String toString() {
    String line = Math.abs(edgeEndpoints.p1 - edgeEndpoints.p2) == 10 ? "|" : "──";
    return line;
  }
}
