package IO;

import java.util.Map;
import java.util.Scanner;

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
      StringBuilder prompt = new StringBuilder("Hi " + playerName + "! Which game would you like to play? ");
      StringBuilder optionsPrompt = new StringBuilder();
      for (Map.Entry<String, String> entry : gameOptions.entrySet()) {
        optionsPrompt.append("Enter '").append(entry.getKey()).append("' for ").append(entry.getValue()).append(": ");
      }
      String input = getStringInput(prompt.toString() + optionsPrompt.toString());
      if (gameOptions.containsKey(input.toLowerCase())) {
        return input;
      } else {
        System.out.println(
            "Invalid game selection. " + "Please enter one of the following options: " + optionsPrompt.toString());
        return getGameSelectionInput(playerName, gameOptions); // recursively call until valid input is received
      }
    }
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

  public int getIntForBoardDimension(String dimensionName, int min, int max) {
    while (true) {
      String prompt = "Enter the number of " + dimensionName + " for the board (" + min + "-" + max + "): ";
      int dimension = getIntInput(prompt);
      if (dimension != -1 && dimension >= min && dimension <= max) {
        return dimension;
      } else {
        System.out.println("Invalid input. Board " + dimensionName + " must be between " + min + " and " + max + ".");
      }
    }
  }

  public void close() {
    scanner.close();
  }
}
