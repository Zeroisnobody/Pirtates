package text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import logic.Archipelago;
import logic.Populate;

/**
 * The User Interface (text-based).

 * @author Bryony
 *
 */
public class Speak {

  private Archipelago game;
  private BufferedReader user;

  public static void main(String[] args) throws IOException {
    new Speak();
  }

  /**
   * Set up game to play using text.

   * @throws IOException Something went wrong with getting the user response.
   */
  public Speak() throws IOException {
    game = new Archipelago();
    user = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Welcome to " + game.getIslandName() + "!");
    System.out.println("Visit the trading posts of the different islands. "
        + "Compare their prices and trade wisely to amass great wealth!");
    System.out.println("Head to the shipyards at Dragon Cove to upgrade "
        + "your ship and dominate the seas!");
    System.out.println("Keep an eye out for talent. Hiring new crew members "
        + "is well worth the price!");
    System.out.println("Set sail?");
    String answer = user.readLine();
    if (answer.equalsIgnoreCase("yes")) {
      game.setSail();
      startJourney();
    }
  }

  private void startJourney() throws IOException {
    if (game.atSea()) {
      System.out.println("You are sailing at " + game.getSpeed() + " knots "
          + "through the clear waters.");
      System.out.println("Where do you want to head?");
      List<String> islands = game.getIslandNames();
      for (int i = 0; i < islands.size(); i++) {
        System.out.println("\t" + islands.get(i) + " (" + i + ")");
      }
      chooseIsland();
    }
  }

  private void chooseIsland() throws IOException {
    String answer = user.readLine();
    switch (answer) {
      case "0": 
        travelTo(0);
        break;
      case "1": 
        travelTo(1);
        break;
      case "2": 
        travelTo(2);
        break;
      case "3": 
        travelTo(3);
        break;
      case "4": 
        travelTo(4);
        break;
      default:
        System.out.println("Please enter the number of the island you want to travel to.");
        chooseIsland();
    }
  }

  private void travelTo(int index) throws IOException {
    game.travelTo(index);
    System.out.println("You have arrived at " + game.getIslandName());

    boolean onLand = true;
    while (onLand) { 
      System.out.println("What do you want to do?");
      System.out.println("Trade (0)");
      System.out.println("Look Around (1)");
      System.out.println("Set Sail (2)");
      onLand = chooseAction();
    }
  }

  private boolean chooseAction() throws IOException {
    String answer = user.readLine();
    switch (answer) {
      case "0": 
        trade();
        return true;
      case "1": 
        lookAround();
        return true;
      case "2":
        startJourney();
        return false;
      default:
        System.out.println("Choose the number of the action you wish to perform.");
        return chooseAction();
    }
  }

  private void lookAround() throws IOException {
    switch (game.getIslandName()) {
      case "Dragon Cove": 
        System.out.println("You find the shipyard.");
        System.out.println("Your current ship:");
        System.out.println("\t" + game.shipDescription());
        if (confirm("Do you want to purchase a new ship?")) {
          buyShip();
        }
        break;
      default:
        if (game.crewMemberAvailable()) {
          System.out.println("You find a " + game.getCrewName());
          if (confirm("Would you like to hire them for " + game.getCrewCost())) {
            System.out.println(game.getCrewName() + " has joined your crew.");
            game.hireCrew();
          }
        } else {
          System.out.println("You find nothing.");
        }
    }
  }

  private void buyShip() throws IOException {
    List<String> ships = game.shipDescriptions();
    for (int i = 0; i < ships.size(); i++) {
      System.out.println("\t" + ships.get(i) + " (" + i + ")");
    }
    System.out.println("\tToo Expensive (" + ships.size() + ")");
    chooseShip(ships.size());
  }

  private void chooseShip(int size) throws IOException {
    String answer = user.readLine();
    try {
      int index = Integer.valueOf(answer);
      if (index >= 0 && index < size) {
        game.buyShip(index);
      } else if (index == size) {
        return;
      } else {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException e) {
      System.out.println("Choose the number of the ship you want to purchase.");
      chooseShip(size);
    }
  }

  private void trade() throws IOException {
    System.out.println("The prices for buying and selling on this island are :");
    System.out.println("\tGrain = D" + game.islandGrain() + "\tSpices = D" + game.islandSpice());
    System.out.println("\tMedicine = D" + game.islandMedicine() + "\tSilk = D" + game.islandSilk());
    System.out.println("Do you wish to purchase (0) or sell (1)?");
    chooseTrade();
  }

  private void chooseTrade() throws IOException {
    String answer = user.readLine();
    switch (answer) {
      case "0": {
        System.out.println("Doubloons = D" + game.wallet() + "\tCargo Space = " 
            + game.cargoSpace() + "/" + game.cargoTotal());
        System.out.println("What do you wish to buy?");
        int index = chooseItem();
        System.out.println("How many do you want to buy?");
        purchase(index);
        break;
      }
      case "1": {
        System.out.println("Grain = " + game.costItem(1, 0) + "\tSpices = " + game.costItem(1, 1));
        System.out.println("Medicine = " + game.costItem(1, 2) + "\tSilk = " + game.costItem(1, 3));
        System.out.println("What do you wish to sell?");
        int index = chooseItem();
        System.out.println("How many do you want to sell?");
        sell(index);
        break;
      }
      default:
        System.out.println("Choose the number of the trade you wish to perform.");
        chooseTrade();
    }
  }

  private int chooseItem() throws IOException {
    String answer = user.readLine().toLowerCase();
    switch (answer) {
      case "grain":
        return 0;
      case "spices":
        return 1;
      case "medicine":
        return 2;
      case "silk":
        return 3;
      default:
        System.out.println("Write the name of the item.");
        return chooseItem();
    }
  }

  private void purchase(int index) throws IOException {
    String answer = user.readLine();
    if (answer.equalsIgnoreCase("max")) {
      int max = game.getMaxItem(index);
      System.out.println("Buying " + max + " " + Populate.item(index) + " for D" 
          + game.costItem(max, index));
      if (confirm()) {
        game.buyItem(max, index);
        System.out.println("Thank you for your purchase.");
      }
    } else {
      try {
        int ans = Math.min(Integer.valueOf(answer), game.getMaxItem(index));
        System.out.println("Buying " + ans + " " + Populate.item(index) + " for D" 
            + game.costItem(ans, index));
        if (confirm()) {
          game.buyItem(ans, index);
          System.out.println("Thank you for your purchase.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Choose either a whole number or max.");
      }
    }
  }

  private boolean confirm() throws IOException {
    return confirm("Confirm?");
  }

  private boolean confirm(String message) throws IOException {
    System.out.println(message);
    String answer = user.readLine();
    if (answer.equalsIgnoreCase("yes")) {
      return true;
    } else if (answer.equalsIgnoreCase("no")) {
      return false;
    } else {
      return confirm(message);
    }
  }

  private void sell(int index) throws IOException {
    String answer = user.readLine();
    if (answer.equalsIgnoreCase("max")) {
      int max = game.captainItem(index);
      System.out.println("Selling " + max + " " + Populate.item(index) + " for D" 
          + game.costItem(max, index));
      if (confirm()) {
        game.sellItem(max, index);
        System.out.println("Thank you for your sale.");
      }
    } else {
      try {
        int ans = Math.min(Integer.valueOf(answer), game.getMaxItem(index));
        System.out.println("Selling " + ans + " " + Populate.item(index) + " for D" 
            + game.costItem(ans, index));
        if (confirm()) {
          game.sellItem(ans, index);
          System.out.println("Thank you for your sale.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Choose either a whole number or max.");
      }
    }
  }

}
