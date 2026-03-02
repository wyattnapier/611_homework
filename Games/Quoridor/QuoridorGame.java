package Games.Quoridor;

import Games.Core.Game;
import Games.Core.LineEndpoints;
import Games.Enums.DotsAndBoxesOwnershipEnum;
import Games.Enums.MoveOutcomeEnum;

public class QuoridorGame extends Game {
  private QuoridorPlayer player1, player2, currentPlayer;
  private QuoridorBoard board;

  public QuoridorGame(QuoridorPlayer player1, QuoridorPlayer player2, Games.IO.Input input) {
    this.player1 = player1;
    this.player2 = player2;
    this.input = input;
    this.MIN_DIMENSION = 9; // fixed size 9x9
    this.MAX_DIMENSION = 9; // fixed size 9x9
  }

  /**
   * controls main game loop and creation and handles game outcome
   * 
   * @param rows is number of rows for the board (must be 9)
   * @param cols is number of cols for the board (must be 9)
   */
  public void playSingleGame(int rows, int cols) {
    assert rows == 9 && cols == 9 : "invalid dimensions: quoridor board must be 9x9";
    board = new QuoridorBoard(rows, cols, player1, player2);
    currentPlayer = player2; // can make random in the future like in dots and boxes game if we want

    // main game loop
    MoveOutcomeEnum gameResult = MoveOutcomeEnum.CONTINUE_PLAYING;
    while (gameResult == MoveOutcomeEnum.CONTINUE_PLAYING) {
      currentPlayer = (currentPlayer.equals(player1)) ? player2 : player1; // swap current player
      gameResult = playSingleMove();
    }

    if (gameResult == MoveOutcomeEnum.QUIT) {
      QuoridorPlayer winner = (currentPlayer.equals(player1)) ? player2 : player1; // other player wins
      winner.incrementGamesWon();
      System.out.println(currentPlayer.getPlayerName() + " quit the game so " + winner.getPlayerName() + " wins!");
    }
    if (gameResult == MoveOutcomeEnum.WIN) {
      QuoridorPlayer winner = (player1.getGoalRow() == player2.getRow()) ? player1 : player2;
      winner.incrementGamesWon();
      System.out.println(winner.getPlayerName() + " wins!");
    }
  }

  /**
   * plays a single move of the game (uses recursion to keep retrying for a valid
   * move)
   * 
   * @return turn outcome such as win, quit, or continue playing
   */
  public MoveOutcomeEnum playSingleMove() {
    // TODO: verify prompt/input aligns with how we handle input
    String prompt = (currentPlayer.getPlayerName()
        + ", enter 'q' to quit, 'w' to mark a wall of length 2 like [w r1 c1 r2 c2], or \n'm' to move your character to a square (use the top left corner's coordinates) like [m r1 c1] \nPut your input here: ");
    String moveInput = input.getRawEndpointInput(prompt);
    System.out.println();

    // core logic based on user input and basic input validation
    String[] moveInputParts = moveInput.split("\\s+");
    switch (moveInputParts[0]) {
      case "win":
        board.setBoardToSolvedState();
        System.out.println(board);
        return MoveOutcomeEnum.WIN;
      case "q":
        return MoveOutcomeEnum.QUIT;
      case "m":
        // move character
        if (moveInputParts.length != 3) {
          System.out.println("Insufficient arguments in input. Try again.\n");
          return playSingleMove(); // recurse to try again
        }
        if (!board.tryMove(currentPlayer, Integer.parseInt(moveInputParts[1]), Integer.parseInt(moveInputParts[2]))) {
          System.out.println("Invalid input. Try again.\n");
          return playSingleMove(); // recurse for another attempt to input valid input
        }
        break;
      case "w":
        // add a wall
        if (moveInputParts.length != 5) {
          System.out.println("Insufficient arguments in input. Try again.\n");
          return playSingleMove(); // recurse to try again
        }
        if (!board.tryPlaceWall(currentPlayer, Integer.parseInt(moveInputParts[1]), Integer.parseInt(moveInputParts[2]),
            Integer.parseInt(moveInputParts[3]), Integer.parseInt(moveInputParts[4]))) {
          System.out.println("Invalid input. Try again.\n");
          return playSingleMove(); // recurse for another attempt to input valid input
        }
        break;
      default:
        System.out.println("Invalid input. Try again.\n");
        return playSingleMove();
    }
    return board.isSolved() ? MoveOutcomeEnum.WIN : MoveOutcomeEnum.CONTINUE_PLAYING;
  }
}
