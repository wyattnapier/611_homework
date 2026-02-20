package Games.Core;

import java.util.*;

import Games.IO.*;
import Games.SlidingPuzzle.SlidingPuzzleGame;
import Games.DotsAndBoxes.DotsAndBoxesGame;

/**
 * This is the main entry point for the game application. It runs the game and
 * controls the very high level control flow, outside of individual games
 * 
 */
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
      String playersNames = player1.getPlayerName();
      if (player2 != null) {
        playersNames = player1.getPlayerName() + " and " + player2.getPlayerName();
      }
      String gameSelection = input.getGameSelectionInput(playersNames, gameOptions);
      Game game = null;
      // controls game flow based on game selection
      switch (gameSelection.toLowerCase()) {
        case "s":
          Player currentPlayer = player1;
          if (player2 != null)
            currentPlayer = input.getSlidingPuzzleCurrentPlayer(player1.getPlayerName(), player2.getPlayerName()) == 1
                ? player1
                : player2;
          game = new SlidingPuzzleGame(currentPlayer, input);
          break;
        case "d":
          if (player2 == null) {
            String player2NameInput;
            while (true) {
              player2NameInput = input.getUsername("Player 2");
              if (player2NameInput.equals(player1.getPlayerName())) {
                System.out.println("Invalid input. Player 2 cannot have same name as Player 1");
              } else {
                break;
              }
            }
            player2 = new Player(player2NameInput);
          }
          game = new DotsAndBoxesGame(player1, player2, input);
          break;
        case "q":
          output.printFinalEndMessage(player1, player2);
          continuePlaying = false;
          break;
        default:
          System.out.println("This game hasn't been implemented yet. Please try again later.");
      }

      if (game != null)
        game.setupAndPlayMultipleGames();
      // add option to quit after playing first game
      if (firstRun) {
        gameOptions.put("q", "quitting all games");
        firstRun = false;
      }
    } while (continuePlaying);
    input.close();
  }

}
