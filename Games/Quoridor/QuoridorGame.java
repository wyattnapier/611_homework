package Games.Quoridor;

import Games.Core.Game;
import Games.Core.Player;
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
    board = new QuoridorBoard(rows, cols, player1, currentPlayer);
    currentPlayer = player1; // can make random in the future like in dots and boxes game if we want

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

  public MoveOutcomeEnum playSingleMove() {
    // TODO: implement single move for quoridor game
    return MoveOutcomeEnum.CONTINUE_PLAYING;
  }
}
