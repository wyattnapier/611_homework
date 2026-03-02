package Games.IO;

import java.util.Map;
import java.util.Scanner;

import Games.Core.LineEndpoints;

/**
 * this class is used to prompt users for input and parse it to clean up the
 * code in other classes
 */
public class Input {
  private Scanner scanner;

  public Input() {
    scanner = new Scanner(System.in);
  }

  // ------------ STRING INPUT METHODS ------------
  /**
   * gets string input from user when provided a prompt
   * 
   * @param prompt is what you want to ask user
   * @return user's string input
   */
  public String getStringInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine();
  }

  /**
   * gets user input "y" or "n" to see if they want to play again or reprompts if
   * input isn't valid
   * 
   * @return y or n based on if user wants to play again or not
   */
  public String getPlayAgainInputString() {
    while (true) {
      String input = getStringInput("Would you like to play again? [y/n] ");
      if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n")) {
        return input;
      } else {
        System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.\n");
      }
    }
  }

  /**
   * get user input for game selection
   * 
   * @param playerName  name of player
   * @param gameOptions map of game options. First String is the character that
   *                    represents it and the second String is a more detailed
   *                    game title
   * @return character for game selected
   */
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
  /**
   * gets string input from user when provided a prompt
   * 
   * @param prompt is what you want to ask user
   * @return user's string input
   */
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

  /**
   * get an int for board size
   * 
   * @param dimensionName either "rows" or "cols"
   * @param min           board min dimension
   * @param max           board max dimension
   * @return int dimension: min <= dimension <= max
   */
  public int getIntForBoardDimension(String dimensionName, int min, int max) {
    String prompt = "\nEnter the number of " + dimensionName + " for the board (" + min + "-" + max + "): ";
    while (true) {
      int dimension = getIntInput(prompt);
      if (dimension != -1 && dimension >= min && dimension <= max)
        return dimension;
      System.out.println("Invalid input. Board " + dimensionName + " must be between " + min + " and " + max + ".");
    }
  }

  /**
   * @return int representing who will be playing the sliding puzzle game
   */
  public int getSlidingPuzzleCurrentPlayer(String player1Name, String player2Name) {
    String prompt = "\nSelect which player will be playing the sliding puzzle game. Enter '1' for " + player1Name
        + " or '2' for "
        + player2Name + ": ";
    while (true) {
      int currentPlayerInt = getIntInput(prompt);
      if (currentPlayerInt == 1 || currentPlayerInt == 2)
        return currentPlayerInt;
      System.out.println("Invalid input. Must enter either 1 or 2.");
    }
  }

  /**
   * gets raw input for endpoint selection (or win/quit) as a string
   * 
   * @param playerName name of current player
   * @return string of "w" "q" or endpoints such as "0 0 0 1"
   */
  public String getRawEndpointInput(String prompt) {
    return getStringInput(prompt).toLowerCase().trim();
  }

  /**
   * parses user input for endpints or quit/win
   * 
   * @param rawEndpointInput String input by the user that hasn't been parsed yet
   *                         like " 0 0 0 1"
   * @return
   */
  public LineEndpoints parseUserInputEndpoints(String rawEndpointInput) {
    if (rawEndpointInput.equals("q") || rawEndpointInput.equals("w")) {
      return null; // null as special signal that it was quit or win command
    }
    try {
      String[] parts = rawEndpointInput.split("\\s+"); // regex for space
      if (parts.length != 4) {
        return new LineEndpoints(-1, -1); // triggers and error and prompts for new input
      }
      int r1 = Integer.parseInt(parts[0]);
      int c1 = Integer.parseInt(parts[1]);
      int r2 = Integer.parseInt(parts[2]);
      int c2 = Integer.parseInt(parts[3]);
      return new LineEndpoints(r1 * 10 + c1, r2 * 10 + c2);
    } catch (Exception e) {
      return new LineEndpoints(-1, -1); // will trigger an error and cause prompting for new input
    }
  }

  /**
   * closes the scanner
   */
  public void close() {
    scanner.close();
  }
}
