package Games.Core;

/* This is the main entry point for the game application */
import java.util.Map;

import Games.IO.Input;
import Games.SlidingPuzzle.SlidingPuzzleGame;

public class Main {
  public static void main(String[] args) {
    Input input = new Input();
    String playerNameInput = input.getStringInput(
        "\nWelcome to my board games! This program allows users to play multiple different types of board games such as Sliding Puzzle and Dots and Boxes.\nTo start off, what is your name Player1? ");
    // used to maintain the game options and their corresponding input keys, making
    // it easy to add more games in the future
    Map<String, String> gameOptions = Map.of(
        "s", "sliding puzzle",
        "d", "dots and boxes");
    String gameSelection = input.getGameSelectionInput(playerNameInput, gameOptions);

    // TODO: add control loop here so that you can select a new game or quit based
    // on return value of the setupAndPlayMultipleGames function
    // TODO: add an enum that is used for each game's return value (create a new
    // folder for enums)
    Game game;
    if (gameSelection.equalsIgnoreCase("s")) {
      game = new SlidingPuzzleGame(playerNameInput, input);
      game.setupAndPlayMultipleGames(); // TODO: move outside of else if block when more games are implemented
    } else if (gameSelection.equalsIgnoreCase("d")) {
      System.out.println("Sorry, dots and boxes is not implemented yet. Please try again later.");
      // game = new DotsAndBoxesGame(player1, player2, input); // TODO: uncomment when
      // dots and boxes is implemented
    }
    // input validation already implemented in Input so else not needed here
    // game.setupAndPlayMultipleGames(); // TODO: uncomment when more games are
    // implemented

    input.close();
  }

}
