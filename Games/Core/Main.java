package Games.Core;

/* This is the main entry point for the game application */
import java.util.Map;

import Games.IO.Input;
import Games.SlidingPuzzle.SlidingPuzzleGame;
import Games.DotsAndBoxes.DotsAndBoxesGame;

public class Main {
  public static void main(String[] args) {
    Input input = new Input();
    String player1NameInput = input.getStringInput(
        "\nWelcome to my board games! This program allows users to play multiple different types of board games such as Sliding Puzzle and Dots and Boxes.\nTo start off, what is your name Player1? ");
    // used to maintain the game options and their corresponding input keys, making
    // it easy to add more games in the future
    Map<String, String> gameOptions = Map.of(
        "s", "sliding puzzle",
        "d", "dots and boxes");
    String gameSelection = input.getGameSelectionInput(player1NameInput, gameOptions);

    // TODO: add control loop here so that you can select a new game or quit based
    // on return value of the setupAndPlayMultipleGames function
    // TODO: add an enum that is used for each game's return value (create a new
    // folder for enums)
    Game game = null;
    if (gameSelection.equalsIgnoreCase("s")) {
      game = new SlidingPuzzleGame(player1NameInput, input);
    } else if (gameSelection.equalsIgnoreCase("d")) {
      // TODO: add a second player here if not already created and pas that into the
      // game
      String player2NameInput = input.getStringInput("\nWhat is player 2's name? ");
      game = new DotsAndBoxesGame(player1NameInput, player2NameInput, input);
    } else {
      System.out.println("TRY AGAIN");
      // TODO: add a loop to force them to try again (maybe in input method)
    }
    if (game != null)
      game.setupAndPlayMultipleGames();
    // input validation already implemented in Input so else not needed here
    // game.setupAndPlayMultipleGames(); // TODO: uncomment when more games are
    // implemented

    input.close();
  }

}
