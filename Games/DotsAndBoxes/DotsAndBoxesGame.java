package Games.DotsAndBoxes;

import Games.IO.Input;
import Games.Core.*;

public class DotsAndBoxesGame extends Game {
  private Input input;
  private int MIN_DIMENSION = 1;
  private int MAX_DIMENSION = 9;
  private Player player1, player2, currentPlayer;

  public DotsAndBoxesGame(Player player1, Player player2, Games.IO.Input input) {
    this.player1 = player1;
    this.player2 = player2;
    this.input = input;
  }

  public void playSingleGame(int rows, int cols) {
    DotsAndBoxesBoard board = new DotsAndBoxesBoard(rows, cols);

    System.out.println(board);
    // TODO: implement game loop
  }

  public MoveOutcome playSingleMove() {
    Endpoints dangerouEndpoints = input.getEndpointsForNewLine(); // unvalidated endpoints
    // TODO: need to implement actually playing a move. 
    return MoveOutcome.WIN; // TODO: remove when real implementation is done
  }

  public int getMinDimension() {
    return MIN_DIMENSION;
  }

  public int getMaxDimension() {
    return MAX_DIMENSION;
  }
}
