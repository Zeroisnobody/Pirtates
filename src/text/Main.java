package text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The Main class for the Text-based User Interface.

 * @author Bryony
 *
 */
public class Main {

  public static void main(String[] args) {
    new Speak(new Input(), new Output());
  }

  /**
   * The output from the game.

   * @author Bryony
   *
   */
  public static class Output {

    public void print(String message) {
      System.out.println(message);
    }

  }

  /**
   * The User Input.

   * @author Bryony
   *
   */
  public static class Input {

    private BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Read the user input.

     * @return The user's input
     */
    public String read() {
      try {
        return read.readLine();
      } catch (IOException e) {
        System.out.println("Something went wrong with System.in");
        e.printStackTrace();
        System.exit(0);
      }
      return "";
    }
  }

}
