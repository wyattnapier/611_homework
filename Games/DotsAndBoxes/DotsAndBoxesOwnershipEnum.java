package Games.DotsAndBoxes;

enum DotsAndBoxesOwnership {
  NOBODY("\u001B[0m"), // RESET
  PLAYER1("\u001B[31m"), // RED
  PLAYER2("\u001B[34m"); // BLUE

  private final String colorCode;
  private static final String RESET = "\u001B[0m";

  DotsAndBoxesOwnership(String colorCode) {
    this.colorCode = colorCode;
  }

  public String getColor() {
    return colorCode;
  }

  public static String getReset() {
    return RESET;
  }
}