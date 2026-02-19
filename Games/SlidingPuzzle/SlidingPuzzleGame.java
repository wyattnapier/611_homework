package Games.SlidingPuzzle;

import Games.Core.Player;
import Games.Core.Game;
import Games.Core.MoveOutcome;
import Games.IO.Input;

/**
 * Class representing the sliding puzzle game, which extends the generic Game
 * class and implements the specific logic for playing a sliding puzzle game. It
 * handles user
 */
public class SlidingPuzzleGame extends Game {
  private Player player;
  private SlidingPuzzleBoard board;
  private final int MIN_DIMENSION = 2;
  private final int MAX_DIMENSION = 9; // reasonable upper limit to avoid huge
  // boards

  /**
   * constructor for Game
   * 
   * @param playerName name of the player
   */
  public SlidingPuzzleGame(Player player, Input input) {
    this.player = player;
    this.input = input;
  }

  /**
   * plays a single game of sliding puzzle
   * 
   * @param rows number of rows for the board
   * @param cols number of columns for the board
   */
  public void playSingleGame(int rows, int cols) {
    // ensure that game isn't already solved when board is created
    do {
      board = new SlidingPuzzleBoard(rows, cols);
    } while (board.isSolved());
    System.out.println(board);

    player.resetNumberOfMoves();

    MoveOutcome continuePlayingFlag = MoveOutcome.CONTINUE_PLAYING; // 1 to continue, 0 to win, -1 to quit
    while (continuePlayingFlag == MoveOutcome.CONTINUE_PLAYING) {
      continuePlayingFlag = playSingleMove();
    }

    // game end message
    if (continuePlayingFlag == MoveOutcome.WIN) {
      player.incrementGamesWon();
      System.out.println("Congratulations! You've won the game!");
      System.out.println("It only took you " + player.getNumberOfMoves() + " moves to win your last game!");
      System.out.println("You've won " + player.getNumberOfGamesWon() + " games!\n");
    } else if (continuePlayingFlag == MoveOutcome.QUIT) {
      System.out.println("You won " + player.getNumberOfGamesWon() + " games!");
    }
  }

  /**
   * plays a single move of sliding puzzle
   * 
   * @return outcome of the move with relation to game loop
   */
  public MoveOutcome playSingleMove() {
    String selectedTileValue = input.getStringInput("Enter the tile number to move or 'q' to quit: ");
    System.out.println();

    switch (selectedTileValue.toLowerCase()) {
      case "q":
        return MoveOutcome.QUIT;
      case "w":
        board.setBoardToSolvedState();
        System.out.println(board);
        return MoveOutcome.WIN;
      default:
        if (board.swapTile(selectedTileValue)) {
          player.incrementMoves();
          System.out.println(board);
          return board.isSolved() ? MoveOutcome.WIN : MoveOutcome.CONTINUE_PLAYING;
        } else {
          System.out.print("Invalid move. ");
          return MoveOutcome.CONTINUE_PLAYING;
        }
    }
  }

  public int getMinDimension() {
    return MIN_DIMENSION;
  }

  public int getMaxDimension() {
    return MAX_DIMENSION;
  }
}
