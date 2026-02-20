package Games.Core;

import java.util.Objects;

public class Endpoints {
  public final int p1;
  public final int p2;

  public Endpoints(int endpoint1, int endpoint2) {
    p1 = endpoint1;
    p2 = endpoint2;
  }

  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Endpoints))
      return false;
    Endpoints c = (Endpoints) o;
    return (p1 == c.p1 && p2 == c.p2) || (p1 == c.p2 && p2 == c.p1); // order doesn't matter for points
  }

  public int hashCode() {
    return Objects.hash(p1, p2);
  }
}
