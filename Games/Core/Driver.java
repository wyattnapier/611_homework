package Games.Core;

/* This is the main entry point for the game application */
import java.util.*;

import Games.IO.*;
import Games.SlidingPuzzle.SlidingPuzzleGame;
import Games.DotsAndBoxes.DotsAndBoxesGame;

public class Driver {
  private Player player1, player2;
  private Input input;
  private Output output;

  public Driver() {
    input = new Input();
    output = new Output();
    output.printWelcomeMessage();
    String player1NameInput = input.getUsername("Player 1");
    player1 = new Player(player1NameInput);
  }

  /**
   * Handles game loop so you can decide which game to play and continue to keep
   * playing after quitting one type. Also manages player1 vs. player2 playing
   */
  public void play() {
    // used to maintain the game options and their corresponding input keys, making
    // it easy to add more games in the future
    Map<String, String> gameOptions = new HashMap<String, String>();
    gameOptions.put("s", "sliding puzzle");
    gameOptions.put("d", "dots and boxes");

    Boolean continuePlaying = true;
    Boolean firstRun = true;
    do {
      String gameSelection = input.getGameSelectionInput(player1.getPlayerName(), gameOptions);
      Game game = null;
      // controls game flow based on game selection
      switch (gameSelection.toLowerCase()) {
        case "s":
          Player currentPlayer = player1;
          if (player2 != null)
            currentPlayer = input.getSlidingPuzzleCurrentPlayer() == 1 ? player1 : player2;
          game = new SlidingPuzzleGame(currentPlayer, input);
          break;
        case "d":
          if (player2 == null) {
            String player2NameInput = input.getUsername("Player 2");
            player2 = new Player(player2NameInput);
          }
          game = new DotsAndBoxesGame(player1, player2, input);
          break;
        case "q":
          output.printFinalEndMessage();
          continuePlaying = false;
          break;
        default:
          System.out.println("This game hasn't been implemented yet. Please try again later.");
      }

      if (game != null)
        game.setupAndPlayMultipleGames();
      // add option to quit after playing first game
      if (firstRun) {
        gameOptions.put("q", "quitting all games"); // TODO: reorder so this is last
        firstRun = false;
      }
    } while (continuePlaying);
    input.close();
  }

}
