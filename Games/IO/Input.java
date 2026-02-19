package Games.IO;

import java.util.Map;
import java.util.Scanner;

import Games.Core.Endpoints;
import Games.DotsAndBoxes.DotsAndBoxesEdge;

public class Input {
  private Scanner scanner;

  public Input() {
    scanner = new Scanner(System.in);
  }

  // ------------ STRING INPUT METHODS ------------
  public String getStringInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine();
  }

  public String getPlayAgainInputString() {
    while (true) {
      String input = getStringInput("Would you like to play again? [y/n] ");
      if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n")) {
        return input;
      } else {
        System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
        return getPlayAgainInputString(); // recursively call until valid input is received
      }
    }
  }

  public String getGameSelectionInput(String playerName, Map<String, String> gameOptions) {
    while (true) {
      StringBuilder prompt = new StringBuilder("\nHi " + playerName + "! Which game would you like to play? ");
      StringBuilder optionsPrompt = new StringBuilder();
      for (Map.Entry<String, String> entry : gameOptions.entrySet()) {
        optionsPrompt.append("\nEnter '").append(entry.getKey()).append("' for ").append(entry.getValue());
      }
      optionsPrompt.append("\nYour selection: ");
      String input = getStringInput(prompt.toString() + optionsPrompt.toString());
      if (gameOptions.containsKey(input.toLowerCase())) {
        return input;
      } else {
        System.out.println(
            "Invalid game selection. Please enter one of the provided options.");
        return getGameSelectionInput(playerName, gameOptions); // recursively call until valid input is received
      }
    }
  }

  /**
   * gets player's username
   * 
   * @param playerTitleString e.g. Player 1 or Player 2
   * @return player's username
   */
  public String getUsername(String playerTitleString) {
    if (playerTitleString == "Player 2") {
      System.out.println(); // adding spacing
    }
    return getStringInput(playerTitleString + " username: ");
  }

  // ------------ INT INPUT METHODS ------------
  public int getIntInput(String prompt) {
    try {
      System.out.print(prompt);
      int output = scanner.nextInt();
      scanner.nextLine(); // consume the newline
      return output;
    } catch (Exception e) {
      System.err.println("Invalid input. Please enter a valid integer."); // not actual error handling for user input,
      // just a message to the programmer
      scanner.nextLine(); // consume the invalid input
      return -1; // return -1 to indicate invalid input
    }
  }

  // TODO: would be good to use an enum for dimensionName probably
  public int getIntForBoardDimension(String dimensionName, int min, int max) {
    String prompt = "\nEnter the number of " + dimensionName + " for the board (" + min + "-" + max + "): ";
    while (true) {
      int dimension = getIntInput(prompt);
      if (dimension != -1 && dimension >= min && dimension <= max)
        return dimension;
      System.out.println("Invalid input. Board " + dimensionName + " must be between " + min + " and " + max + ".");
    }
  }

  public int getSlidingPuzzleCurrentPlayer() {
    String prompt = "\nSelect which player will be playing the sliding puzzle game [1/2]: ";
    while (true) {
      int currentPlayerInt = getIntInput(prompt);
      if (currentPlayerInt == 1 || currentPlayerInt == 2)
        return currentPlayerInt;
      System.out.println("Invalid input. Must enter either 1 or 2.");
    }
  }

  public Endpoints getEndpointsForNewLine(String playerName) {
    String prompt = "\n" + playerName + ", select the points that you would like to draw a line between." +
        " Enter them in the following format: r1 c1 r2 c2.\nInput the points here (space separated): ";
    while (true) {
      try {
        System.out.println(prompt);
        int r1 = scanner.nextInt();
        int c1 = scanner.nextInt();
        int r2 = scanner.nextInt();
        int c2 = scanner.nextInt();
        scanner.nextLine(); // consumes the newline
        return new Endpoints(r1 * 10 + c1, r2 * 10 + c2);
      } catch (Exception e) {
        return new Endpoints(-1, -1); // will trigger an error and cause prompting for new input
      }
    }
  }

  public void close() {
    scanner.close();
  }
}
