package Games.DotsAndBoxes;

import Games.IO.Input;
import Games.Core.*;

public class DotsAndBoxesGame implements Game {
  private Input input;

  public DotsAndBoxesGame(String playerName, Games.IO.Input input) {
    this.input = input;
  }

  public void setupAndPlayMultipleGames() {
    return; // TODO: implement when Dots and Boxes is implemented
  }

  public void playMultipleGames(int rows, int cols) {
    return; // TODO: implement when Dots and Boxes is implemented
  }

  public void playSingleGame(int rows, int cols) {
    DotsAndBoxesBoard board = createBoard(rows, cols);
    // TODO: implement game loop
  }

  public DotsAndBoxesBoard createBoard(int rows, int cols) {
    return null; // TODO: implement when Dots and Boxes is implemented
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
