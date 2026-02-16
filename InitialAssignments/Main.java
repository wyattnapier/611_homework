/* This is the main entry point for the game application */

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    System.out.print("Welcome to my sliding puzzle game! What is your name? ");
    Scanner scan = new Scanner(System.in);
    String playerNameInput = scan.nextLine();

    Game game = new Game(playerNameInput);
    game.setupAndPlayMultipleGames(scan);

    scan.close();
  }

}
