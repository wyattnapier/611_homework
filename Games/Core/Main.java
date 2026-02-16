package Games.Core;

/* This is the main entry point for the game application */
import java.util.Map;

import Games.IO.Input;
import Games.SlidingPuzzle.SlidingPuzzleGame;

public class Main {
  public static void main(String[] args) {
    Input input = new Input();
    String playerNameInput = input.getStringInput("Welcome to my board games! What is your name? ");
    // used to maintain the game options and their corresponding input keys, making
    // it easy to add more games in the future
    Map<String, String> gameOptions = Map.of(
        "s", "sliding puzzle",
        "d", "dots and boxes");
    String gameSelection = input.getGameSelectionInput(playerNameInput, gameOptions);

    if (gameSelection.equalsIgnoreCase("s")) {
      Game game = new SlidingPuzzleGame(playerNameInput, input);
      game.setupAndPlayMultipleGames(); // TODO: move outside of else if block when more games are implemented
    } else if (gameSelection.equalsIgnoreCase("d")) {
      System.out.println("Sorry, dots and boxes is not implemented yet. Please try again later.");
    }
    // input validation already implemented in Input so else not needed here
    // game.setupAndPlayMultipleGames(); // TODO: uncomment when more games are
    // implemented

    input.close();
  }

}
