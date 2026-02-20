package Games.DotsAndBoxes;

import java.util.Random;
import Games.Core.*;

public class DotsAndBoxesGame extends Game {
  private int MIN_DIMENSION = 1;
  private int MAX_DIMENSION = 9;
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
  }

  public void playSingleGame(int rows, int cols) {
    board = new DotsAndBoxesBoard(rows, cols);
    Boolean randomPlayerBool = rand.nextBoolean();
    currentPlayer = randomPlayerBool ? player1 : player2;

    MoveOutcome gameResult = MoveOutcome.CONTINUE_PLAYING;
    while (gameResult == MoveOutcome.CONTINUE_PLAYING) {
      currentPlayer = (currentPlayer.equals(player1)) ? player2 : player1; // swap current player
      gameResult = playSingleMove();
    }

    printGameStatus();

    if (gameResult == MoveOutcome.QUIT) {
      Player winner = (currentPlayer.equals(player1)) ? player2 : player1; // other player wins
      winner.incrementGamesWon();
      System.out.println(currentPlayer.getPlayerName() + " quit the game so " + winner.getPlayerName() + " wins!");
    }
    if (gameResult == MoveOutcome.WIN) {
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

  public MoveOutcome playSingleMove() {
    int numCompletedTiles = board.getNumberOfCompletedTiles();
    printGameStatus();
    DotsAndBoxesOwnership currentOwner = currentPlayer.equals(player1) ? DotsAndBoxesOwnership.PLAYER1
        : DotsAndBoxesOwnership.PLAYER2;
    // attempt to mark an edge or quit game
    Endpoints points = null;
    Boolean edgeSuccessfullyDrawn = false;
    while (!edgeSuccessfullyDrawn) {
      points = getUserInputEndpoints();
      // checks for special cases like 'q' and 'w'
      if (points == null) {
        if (board.isSolved()) {
          return MoveOutcome.WIN;
        }
        return MoveOutcome.QUIT;
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
      return MoveOutcome.WIN;
    } else if (board.getNumberOfCompletedTiles() > numCompletedTiles) {
      return playSingleMove(); // recursively call it to see future outcome
    } else {
      return MoveOutcome.CONTINUE_PLAYING;
    }
  }

  /**
   * handles user turn input which can either be quit/win/endpoints for line
   * if win selected, it sets board to solved state for testing
   * 
   * @return user selected valid endpoints or null for quit or win
   */
  private Endpoints getUserInputEndpoints() {
    String currentPlayerName = currentPlayer.getPlayerName();
    while (true) {
      String raw = input.getRawEndpointInput(currentPlayerName); // Get the string "q", "w", or "0 0 0 1"

      if (raw.equals("q")) {
        return null; // Signal to playSingleMove that the user quit
      }

      if (raw.equals("w")) {
        board.setBoardToSolvedState();
        // update the player win count by counting the number of squares
        player1.setNumberOfBoxesOwner(board.countNumberOfBoxesOwnedByUser(DotsAndBoxesOwnership.PLAYER1));
        player2.setNumberOfBoxesOwner(board.countNumberOfBoxesOwnedByUser(DotsAndBoxesOwnership.PLAYER2));
        return null; // Signal to playSingleMove to check for a win immediately
      }

      Endpoints points = input.parseUserInputEndpoints(raw);
      if (board.isValidEdge(points)) {
        return points;
      }
      System.out.println("Invalid input. Try again.\n");
    }
  }

  private void printGameStatus() {
    printScoreboard();
    System.out.println(board);
  }

  private void printScoreboard() {
    String reset = DotsAndBoxesOwnership.getReset();
    String p1Color = DotsAndBoxesOwnership.PLAYER1.getColor();
    String p2Color = DotsAndBoxesOwnership.PLAYER2.getColor();
    System.out.println("\n============================");
    System.out.println("      CURRENT SCORE");
    System.out.println(String.format("  %s%s%s: %d boxes",
        p1Color, player1.getPlayerName(), reset, player1.getNumberOfBoxesOwned()));
    System.out.println(String.format("  %s%s%s: %d boxes",
        p2Color, player2.getPlayerName(), reset, player2.getNumberOfBoxesOwned()));
    System.out.println("============================");
  }

  public int getMinDimension() {
    return MIN_DIMENSION;
  }

  public int getMaxDimension() {
    return MAX_DIMENSION;
  }
}
