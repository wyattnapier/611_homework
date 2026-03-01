package Games.IO;

import Games.Core.Player;
import Games.Enums.DotsAndBoxesOwnershipEnum;

/**
 * minimal class for outputting messages related to overall flow
 */
public class Output {
  /**
   * prints welcome message
   */
  public void printWelcomeMessage() {
    System.out.println(
        "\nWelcome to my board games! This program allows users to play multiple different types of board games such as Sliding Puzzle and Dots and Boxes.");
  }

  /**
   * game description
   */
  public void printDotsAndBoxesDescription() {
    System.out.println(
        "Dots and Boxes is a strategic pen-and-paper game where two players take turns drawing lines between dots on a grid to complete squares and claim them as their own.");
  }

  /**
   * game description
   */
  public void printSlidingPuzzleDescription() {
    System.out.println(
        "The sliding puzzle is a single-player game where the objective is to arrange a set of numbered square tiles in ascending order by sliding them one at a time into a single empty space within a framed grid.");
  }

  public void printQuoridorDescription() {
    System.out.println(
        "Quorridor is a two player game where your objective is to cross the board. \nYou can place walls on the map to slow your opponent down -- first to the other side wins!");
  }

  /**
   * prints final message including player stats
   * 
   * @param p1 player 1
   * @param p2 player 2
   */
  public void printFinalEndMessage(Player p1, Player p2) {
    System.out.println("\nFarewell, thank you for playing my games!");
    System.out.println(
        DotsAndBoxesOwnershipEnum.PLAYER1.getColor() + p1.getPlayerName() + DotsAndBoxesOwnershipEnum.getReset()
            + " won "
            + p1.getNumberOfGamesWon() + " games");
    if (p2 != null) {
      System.out.println(
          DotsAndBoxesOwnershipEnum.PLAYER2.getColor() + p2.getPlayerName() + DotsAndBoxesOwnershipEnum.getReset()
              + " won "
              + p2.getNumberOfGamesWon() + " games");
    }
  }
}
