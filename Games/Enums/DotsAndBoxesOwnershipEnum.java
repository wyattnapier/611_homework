package Games.Enums;

/**
 * enum used to keep track of current owner of dots and boxes without having to
 * share player objects with individual tiles and edges
 */
public enum DotsAndBoxesOwnershipEnum {
  NOBODY("\u001B[0m"), // RESET
  PLAYER1("\u001B[31m"), // RED
  PLAYER2("\u001B[34m"); // BLUE

  private final String colorCode;
  private static final String RESET = "\u001B[0m";

  DotsAndBoxesOwnershipEnum(String colorCode) {
    this.colorCode = colorCode;
  }

  /**
   * 
   * @return colorCode associated with enum value for printing
   */
  public String getColor() {
    return colorCode;
  }

  /**
   * 
   * @return colorcode for reset for printing
   */
  public static String getReset() {
    return RESET;
  }
}