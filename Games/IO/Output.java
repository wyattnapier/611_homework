package Games.IO;

import Games.Core.Player;
import Games.Enums.DotsAndBoxesOwnership;

public class Output {
  public void printWelcomeMessage() {
    System.out.println(
        "\nWelcome to my board games! This program allows users to play multiple different types of board games such as Sliding Puzzle and Dots and Boxes.");
  }

  public void printFinalEndMessage(Player p1, Player p2) {
    System.out.println("\nFarewell, thank you for playing my games!");
    System.out.println(
        DotsAndBoxesOwnership.PLAYER1.getColor() + p1.getPlayerName() + DotsAndBoxesOwnership.getReset() + " won "
            + p1.getNumberOfGamesWon() + " games");
    if (p2 != null) {
      System.out.println(
          DotsAndBoxesOwnership.PLAYER2.getColor() + p2.getPlayerName() + DotsAndBoxesOwnership.getReset() + " won "
              + p2.getNumberOfGamesWon() + " games");
    }
  }

  // TODO: need to override in other versions?
  // public abstract void printGameDescription();
  // public abstract void printBoard();
}
