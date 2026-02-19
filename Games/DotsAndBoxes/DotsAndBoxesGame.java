package Games.DotsAndBoxes;

import Games.IO.Input;

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
    int randomPlayerInt = rand.nextInt(2) + 1;
    currentPlayer = (randomPlayerInt == 1) ? player1 : player2;

    MoveOutcome gameResult = MoveOutcome.CONTINUE_PLAYING;
    while (gameResult == MoveOutcome.CONTINUE_PLAYING) {
      currentPlayer = (currentPlayer.equals(player1)) ? player2 : player1; // swap current player
      gameResult = playSingleMove();
    }

    if (gameResult == MoveOutcome.QUIT) {
      Player winner = (currentPlayer.equals(player1)) ? player2 : player1; // other player wins
      winner.incrementGamesWon();
      System.out.println(currentPlayer.getPlayerName() + " quit the game so " + winner.getPlayerName() + " wins!");
    }
    if (gameResult == MoveOutcome.WIN) {
      int player1NumBoxesOwned = player1.getNumberOfBoxesOwned();
      int player2NumBoxesOwned = player2.getNumberOfBoxesOwned();
      System.out.println(player1.getPlayerName() + " had " + player1NumBoxesOwned + " and " + player2.getPlayerName()
          + " had " + player2NumBoxesOwned);
      if (player1NumBoxesOwned == player2NumBoxesOwned) {
        System.out.println("The game is a tie!");
      } else {
        Player winner = player1NumBoxesOwned > player2NumBoxesOwned ? player1 : player2;
        winner.incrementGamesWon();
        System.out.println(winner.getPlayerName() + "wins!");
      }
    }
  }

  public MoveOutcome playSingleMove() {
    int numCompletedTiles = board.getNumberOfCompletedTiles();
    System.out.println(board);
    DotsAndBoxesOwnership currentOwner = currentPlayer.equals(player1) ? DotsAndBoxesOwnership.PLAYER1
        : DotsAndBoxesOwnership.PLAYER2;
    Boolean edgeSuccessfullyDrawn = false;
    while (!edgeSuccessfullyDrawn) {
      Endpoints points = getUserInputEndpoints();
      edgeSuccessfullyDrawn = board.markEdge(points, currentOwner);
      if (!edgeSuccessfullyDrawn) {
        System.out.println("You can't place an edge there. Try again.");
      }
    }
    // TODO: need to figure out how to add a quit early option
    if (board.isSolved()) {
      return MoveOutcome.WIN;
    } else if (board.getNumberOfCompletedTiles() > numCompletedTiles) {
      for (int i = 0; i < board.getNumberOfCompletedTiles() - numCompletedTiles; i++) {
        currentPlayer.incrementNumberOfBoxesOwned(); // increment based on number of tiles completed
      }
      return playSingleMove(); // recursively call it to see future outcome
    } else {
      return MoveOutcome.CONTINUE_PLAYING;
    }
  }

  private Endpoints getUserInputEndpoints() {
    String currentPlayerName = currentPlayer.getPlayerName();
    Endpoints dangerousEndpoints = input.getEndpointsForNewLine(currentPlayerName); // unvalidated endpoints
    while (!board.isValidEdge(dangerousEndpoints)) {
      System.out.println("Invalid input. Try again.\n");
      dangerousEndpoints = input.getEndpointsForNewLine(currentPlayerName); // get new pair
    }
    Endpoints validEndpoints = dangerousEndpoints;
    return validEndpoints;
  }

  public int getMinDimension() {
    return MIN_DIMENSION;
  }

  public int getMaxDimension() {
    return MAX_DIMENSION;
  }
}
