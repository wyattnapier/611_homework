package Games.DotsAndBoxes;

import Games.IO.Input;
import Games.Core.*;

public class DotsAndBoxesGame implements Game {
  private Input input;
  private int MIN_DIMENSION = 1;
  private int MAX_DIMENSION = 9;
  private Player player1, player2;

  public DotsAndBoxesGame(Player player1, Player player2, Games.IO.Input input) {
    this.player1 = player1;
    this.player2 = player2;
    this.input = input;
  }

  public void setupAndPlayMultipleGames() {
    int rows = input.getIntForBoardDimension("rows", MIN_DIMENSION, MAX_DIMENSION);
    int cols = input.getIntForBoardDimension("columns", MIN_DIMENSION, MAX_DIMENSION);
    playMultipleGames(rows, cols);
    return; // TODO: implement when Dots and Boxes is implemented
  }

  public void playMultipleGames(int rows, int cols) {
    playSingleGame(rows, cols);
    return; // TODO: implement when Dots and Boxes is implemented
  }

  public void playSingleGame(int rows, int cols) {
    DotsAndBoxesBoard board = new DotsAndBoxesBoard(rows, cols);
    System.out.println(board);
    // TODO: implement game loop
  }

  public int playSingleMove() {
    return 0; // TODO: implement when Dots and Boxes is implemented
  }

  public int getValidBoardDimension(String dimensionName) {
    return 0; // TODO: implement when Dots and Boxes is implemented
  }

  public Board getBoard() {
    return null; // TODO: implement when Dots and Boxes is implemented
  }

  public Player getPlayer() {
    return null; // TODO: implement when Dots and Boxes is implemented
  }

}
