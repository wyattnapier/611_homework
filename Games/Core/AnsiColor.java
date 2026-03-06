package Games.Core;

public final class AnsiColor {
  public static final String RESET = "\u001B[0m";
  public static final String WALL = "\u001B[33m";
  public static final String HIGHLIGHT = "\u001B[32m";

  private AnsiColor() {
  }

  public static String wrap(String color, String text) {
    return color + text + RESET;
  }
}
