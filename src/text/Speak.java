package text;

import java.util.List;
import logic.Archipelago;
import logic.Populate;
import text.Main.Input;
import text.Main.Output;

/**
 * The User Interface (text-based).

 * @author Bryony
 *
 */
public class Speak {

  private Archipelago game;
  private Input user;
  private Output out;

  /**
   * Set up game to play using text.

   * @Something went wrong with getting the user response.
   */
  public Speak(Input user, Output out) {
    game = new Archipelago();
    this.user = user;
    this.out = out;
    out.print("Welcome to " + game.getIslandName() + "!");
    out.print("Visit the trading posts of the different islands. "
        + "Compare their prices and trade wisely to amass great wealth!");
    out.print("Head to the shipyards at Dragon Cove to upgrade "
        + "your ship and dominate the seas!");
    out.print("Keep an eye out for talent. Hiring new crew members "
        + "is well worth the price!");
    if (confirm("Set sail?")) {
      game.setSail();
      startJourney();
    }
  }

  private void startJourney() {
    if (game.atSea()) {
      out.print("You are sailing at " + game.getSpeed() + " knots "
          + "through the clear waters.");
      out.print("Where do you want to head?");
      List<String> islands = game.getIslandNames();
      for (int i = 0; i < islands.size(); i++) {
        out.print("\t" + islands.get(i) + " (" + i + ")");
      }
      chooseIsland();
    }
  }

  private void chooseIsland() {
    String answer = user.read();
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
        out.print("Please enter the number of the island you want to travel to.");
        chooseIsland();
    }
  }

  private void travelTo(int index) {
    game.travelTo(index);
    out.print("You have arrived at " + game.getIslandName());

    boolean onLand = true;
    while (onLand) { 
      out.print("What do you want to do?");
      out.print("Trade (0)");
      out.print("Look Around (1)");
      out.print("Set Sail (2)");
      onLand = chooseAction();
    }
  }

  private boolean chooseAction() {
    String answer = user.read();
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
        out.print("Choose the number of the action you wish to perform.");
        return chooseAction();
    }
  }

  private void lookAround() {
    switch (game.getIslandName()) {
      case "Dragon Cove": 
        out.print("You find the shipyard.");
        out.print("Your current ship:");
        out.print("\t" + game.shipDescription());
        if (confirm("Do you want to purchase a new ship?")) {
          buyShip();
        }
        break;
      default:
        if (game.crewMemberAvailable()) {
          out.print("You find a " + game.getCrewName());
          if (confirm("Would you like to hire them for " + game.getCrewCost())) {
            out.print(game.getCrewName() + " has joined your crew.");
            game.hireCrew();
          }
        } else {
          out.print("You find nothing.");
        }
    }
  }

  private void buyShip() {
    List<String> ships = game.shipDescriptions();
    for (int i = 0; i < ships.size(); i++) {
      out.print("\t" + ships.get(i) + " (" + i + ")");
    }
    out.print("\tToo Expensive (" + ships.size() + ")");
    chooseShip(ships.size());
  }

  private void chooseShip(int size) {
    String answer = user.read();
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
      out.print("Choose the number of the ship you want to purchase.");
      chooseShip(size);
    }
  }

  private void trade() {
    out.print("The prices for buying and selling on this island are :");
    out.print("\tGrain = D" + game.islandGrain() + "\tSpices = D" + game.islandSpice());
    out.print("\tMedicine = D" + game.islandMedicine() + "\tSilk = D" + game.islandSilk());
    out.print("Do you wish to purchase (0) or sell (1)?");
    chooseTrade();
  }

  private void chooseTrade() {
    String answer = user.read();
    switch (answer) {
      case "0": {
        out.print("Doubloons = D" + game.wallet() + "\tCargo Space = " 
            + game.cargoSpace() + "/" + game.cargoTotal());
        out.print("What do you wish to buy?");
        int index = chooseItem();
        out.print("How many do you want to buy?");
        purchase(index);
        break;
      }
      case "1": {
        out.print("Grain = " + game.costItem(1, 0) + "\tSpices = " + game.costItem(1, 1));
        out.print("Medicine = " + game.costItem(1, 2) + "\tSilk = " + game.costItem(1, 3));
        out.print("What do you wish to sell?");
        int index = chooseItem();
        out.print("How many do you want to sell?");
        sell(index);
        break;
      }
      default:
        out.print("Choose the number of the trade you wish to perform.");
        chooseTrade();
    }
  }

  private int chooseItem() {
    String answer = user.read().toLowerCase();
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
        out.print("Write the name of the item.");
        return chooseItem();
    }
  }

  private void purchase(int index) {
    String answer = user.read();
    if (answer.equalsIgnoreCase("max")) {
      int max = game.getMaxItem(index);
      out.print("Buying " + max + " " + Populate.item(index) + " for D" 
          + game.costItem(max, index));
      if (confirm()) {
        game.buyItem(max, index);
        out.print("Thank you for your purchase.");
      }
    } else {
      try {
        int ans = Math.min(Integer.valueOf(answer), game.getMaxItem(index));
        out.print("Buying " + ans + " " + Populate.item(index) + " for D" 
            + game.costItem(ans, index));
        if (confirm()) {
          game.buyItem(ans, index);
          out.print("Thank you for your purchase.");
        }
      } catch (NumberFormatException e) {
        out.print("Choose either a whole number or max.");
      }
    }
  }

  private boolean confirm() {
    return confirm("Confirm?");
  }

  private boolean confirm(String message) {
    out.print(message);
    String answer = user.read();
    if (answer.equalsIgnoreCase("yes")) {
      return true;
    } else if (answer.equalsIgnoreCase("no")) {
      return false;
    } else {
      return confirm(message);
    }
  }

  private void sell(int index) {
    String answer = user.read();
    if (answer.equalsIgnoreCase("max")) {
      int max = game.captainItem(index);
      out.print("Selling " + max + " " + Populate.item(index) + " for D" 
          + game.costItem(max, index));
      if (confirm()) {
        game.sellItem(max, index);
        out.print("Thank you for your sale.");
      }
    } else {
      try {
        int ans = Math.min(Integer.valueOf(answer), game.getMaxItem(index));
        out.print("Selling " + ans + " " + Populate.item(index) + " for D" 
            + game.costItem(ans, index));
        if (confirm()) {
          game.sellItem(ans, index);
          out.print("Thank you for your sale.");
        }
      } catch (NumberFormatException e) {
        out.print("Choose either a whole number or max.");
      }
    }
  }

}
