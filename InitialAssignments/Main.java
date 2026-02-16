
/* This is the main entry point for the game application */
import java.util.Map;

import IO.Input;
import SlidingPuzzle.SlidingPuzzleGame;

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
    } else if (gameSelection.equalsIgnoreCase("d")) {
      System.out.println("Sorry, dots and boxes is not implemented yet. Please try again later.");
    }
    // input validation already implemented in Input so else not needed here
    Game game = new Game(playerNameInput, input);
    game.setupAndPlayMultipleGames();

    input.close();
  }

}
