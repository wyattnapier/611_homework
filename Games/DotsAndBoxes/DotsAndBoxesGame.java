package Games.DotsAndBoxes;

import java.util.Random;
import Games.Core.*;
import Games.Enums.DotsAndBoxesOwnershipEnum;
import Games.Enums.MoveOutcomeEnum;

/**
 * This class controls the high level game logic of the Dots and Boxes game. It
 * controls taking turns and checks for a win or early exit (quit). Uses the
 * superclass method to setup game
 */
public class DotsAndBoxesGame extends Game {
  private Player player1, player2, currentPlayer;
  private DotsAndBoxesBoard board;
  private Random rand;

  public DotsAndBoxesGame(Player player1, Player player2, Games.IO.Input input) {
    this.player1 = player1;
    this.player2 = player2;
    this.input = input;
    player1.resetNumberOfBoxesOwned();
    player2.resetNumberOfBoxesOwned();
    rand = new Random(); // used to randomly choose first player
    this.MIN_DIMENSION = 1;
    this.MAX_DIMENSION = 9;
  }

  /**
   * controls main game loop and creation and handles game outcome
   * 
   * @param rows is number of rows for the board
   * @param cols is number of cols for the board
   */
  public void playSingleGame(int rows, int cols) {
    board = new DotsAndBoxesBoard(rows, cols);
    Boolean randomPlayerBool = rand.nextBoolean();
    currentPlayer = randomPlayerBool ? player1 : player2;

    // main game loop
    MoveOutcomeEnum gameResult = MoveOutcomeEnum.CONTINUE_PLAYING;
    while (gameResult == MoveOutcomeEnum.CONTINUE_PLAYING) {
      currentPlayer = (currentPlayer.equals(player1)) ? player2 : player1; // swap current player
      gameResult = playSingleMove();
    }

    printGameStatus();

    if (gameResult == MoveOutcomeEnum.QUIT) {
      Player winner = (currentPlayer.equals(player1)) ? player2 : player1; // other player wins
      winner.incrementGamesWon();
      System.out.println(currentPlayer.getPlayerName() + " quit the game so " + winner.getPlayerName() + " wins!");
    }
    if (gameResult == MoveOutcomeEnum.WIN) {
      int player1NumBoxesOwned = player1.getNumberOfBoxesOwned();
      int player2NumBoxesOwned = player2.getNumberOfBoxesOwned();
      if (player1NumBoxesOwned == player2NumBoxesOwned) {
        System.out.println("The game is a tie!");
      } else {
        Player winner = player1NumBoxesOwned > player2NumBoxesOwned ? player1 : player2;
        winner.incrementGamesWon();
        System.out.println(winner.getPlayerName() + " wins!");
      }
    }
  }

  /**
   * prompts a user to input their move and marks the edge if it is valid. Also
   * handles the w and q commands to default to winning or quitting. If user
   * completes a tile with their move, they'll get another move and this method
   * will recursively call itself in order to fulfill that requirement
   * 
   * @return an enum that represents the game being won, quit, or to continue
   *         playing
   */
  public MoveOutcomeEnum playSingleMove() {
    int numCompletedTiles = board.getNumberOfCompletedTiles();
    printGameStatus();
    DotsAndBoxesOwnershipEnum currentOwner = currentPlayer.equals(player1) ? DotsAndBoxesOwnershipEnum.PLAYER1
        : DotsAndBoxesOwnershipEnum.PLAYER2;
    // attempt to mark an edge or quit game
    LineEndpoints points = null;
    Boolean edgeSuccessfullyDrawn = false;
    while (!edgeSuccessfullyDrawn) {
      points = getUserInputEndpoints();
      // checks for special cases like 'q' and 'w'
      if (points == null) {
        if (board.isSolved()) {
          return MoveOutcomeEnum.WIN;
        }
        return MoveOutcomeEnum.QUIT;
      }
      edgeSuccessfullyDrawn = board.markEdge(points, currentOwner);
      if (!edgeSuccessfullyDrawn) {
        System.out.println("You can't place an edge there. Try again.");
      }
    }
    // handle a successful move
    // first increment number of boxes owned by current user
    for (int i = 0; i < board.getNumberOfCompletedTiles() - numCompletedTiles; i++) {
      currentPlayer.incrementNumberOfBoxesOwned(); // increment based on number of tiles completed
    }
    // let user play another round if they completed a tile
    if (board.isSolved()) {
      System.out.println("within single move the game is done");
      return MoveOutcomeEnum.WIN;
    } else if (board.getNumberOfCompletedTiles() > numCompletedTiles) {
      return playSingleMove(); // recursively call function so you get another turn if you complete a tile
    } else {
      return MoveOutcomeEnum.CONTINUE_PLAYING;
    }
  }

  /**
   * handles user turn input which can either be quit/win/endpoints for line
   * if win selected, it sets board to solved state for testing
   * 
   * @return user selected valid endpoints or null for quit or win
   */
  private LineEndpoints getUserInputEndpoints() {
    String currentPlayerName = currentPlayer.getPlayerName();
    while (true) {
      String raw = input.getRawEndpointInput(currentPlayerName); // Get the string "q", "w", or "0 0 0 1"

      if (raw.equals("q")) {
        return null; // Signal to playSingleMove that the user quit
      }

      if (raw.equals("w")) {
        board.setBoardToSolvedState();
        // update the player win count by counting the number of squares
        player1.setNumberOfBoxesOwned(board.countNumberOfBoxesOwnedByUser(DotsAndBoxesOwnershipEnum.PLAYER1));
        player2.setNumberOfBoxesOwned(board.countNumberOfBoxesOwnedByUser(DotsAndBoxesOwnershipEnum.PLAYER2));
        return null; // Signal to playSingleMove to check for a win immediately
      }

      LineEndpoints points = input.parseUserInputEndpoints(raw);
      if (board.isValidEdge(points)) {
        return points;
      }
      System.out.println("Invalid input. Try again.\n");
    }
  }

  /**
   * prints game status -- first leaderboard then the board
   */
  private void printGameStatus() {
    printScoreboard();
    System.out.println(board);
  }

  /**
   * prints the scoreboard
   */
  private void printScoreboard() {
    String reset = DotsAndBoxesOwnershipEnum.getReset();
    String p1Color = DotsAndBoxesOwnershipEnum.PLAYER1.getColor();
    String p2Color = DotsAndBoxesOwnershipEnum.PLAYER2.getColor();
    System.out.println("\n============================");
    System.out.println("      CURRENT SCORE");
    System.out.println(String.format("  %s%s%s: %d boxes",
        p1Color, player1.getPlayerName(), reset, player1.getNumberOfBoxesOwned()));
    System.out.println(String.format("  %s%s%s: %d boxes",
        p2Color, player2.getPlayerName(), reset, player2.getNumberOfBoxesOwned()));
    System.out.println("============================");
  }
}
