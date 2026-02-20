package Games.IO;

import Games.Core.Player;
import Games.Enums.DotsAndBoxesOwnershipEnum;

public class Output {
  public void printWelcomeMessage() {
    System.out.println(
        "\nWelcome to my board games! This program allows users to play multiple different types of board games such as Sliding Puzzle and Dots and Boxes.");
  }

  public void printFinalEndMessage(Player p1, Player p2) {
    System.out.println("\nFarewell, thank you for playing my games!");
    System.out.println(
        DotsAndBoxesOwnershipEnum.PLAYER1.getColor() + p1.getPlayerName() + DotsAndBoxesOwnershipEnum.getReset() + " won "
            + p1.getNumberOfGamesWon() + " games");
    if (p2 != null) {
      System.out.println(
          DotsAndBoxesOwnershipEnum.PLAYER2.getColor() + p2.getPlayerName() + DotsAndBoxesOwnershipEnum.getReset() + " won "
              + p2.getNumberOfGamesWon() + " games");
    }
  }

  // TODO: need to override in other versions?
  // public abstract void printGameDescription();
  // public abstract void printBoard();
}
