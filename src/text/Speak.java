package text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import logic.Archipelago;
import logic.Populate;
import text.Main.Input;
import text.Main.Output;

/**
 * The User Interface (text-based).

 * @author Bryony
 * @param <R>
 *
 */
public class Speak<R> {

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
    setSail();
  }
  
  private void setSail() {
    Action action = Action.ONLAND;
    while (action == Action.ONLAND) {
      action = getAnswer(() -> {
        game.setSail();
        rounds();
        return Action.QUIT;
      }, Action.QUIT, Action.ONLAND);
    }
  }

  private enum Action {
    ATSEA,
    CHOOSEISLAND,
    TRAVELTO,
    ONLAND,
    CHOOSEACTION,
    TRADE,
    LOOKAROUND,
    CONFIRMBUYSHIP,
    BUYSHIP,
    CHOOSESHIP,
    CHOOSETRADE,
    CHOOSEITEMBUY,
    CHOOSEITEMSELL,
    PURCHASE,
    CONFIRMPURCHASE,
    SELL,
    CONFIRMSELL,
    QUIT,
    HIRECREW
  }

  private void rounds() {
    boolean running = true;
    Action action = Action.ATSEA;
    ArrayList<Integer> savedNumber = new ArrayList<>();
    while (running) {
      switch (action) {
        case ATSEA: {
          out.print("You are sailing at " + game.getSpeed() + " knots "
              + "through the clear waters.");
          out.print("Where do you want to head?");
          List<String> islands = game.getIslandNames();
          for (int i = 0; i < islands.size(); i++) {
            out.print("\t" + islands.get(i) + " (" + i + ")");
          }
          action = Action.CHOOSEISLAND;
          break;
        }
        case CHOOSEISLAND: {
          Function<String, Action> basic = a -> {
            out.print("\"Please enter the "
                + "number of the island you want to travel to.");
            return Action.CHOOSEISLAND;
          };
          Function<String, Action> other = a -> {
            savedNumber.add(Integer.valueOf(a));
            return Action.TRAVELTO;
          };
          action = getAnswer(basic, other, 5);
          break;
        }
        case TRAVELTO: {
          game.travelTo(savedNumber.remove(0));
          out.print("You have arrived at " + game.getIslandName());
          action = Action.ONLAND;
          break;
        }
        case ONLAND: {
          out.print("What do you want to do?");
          out.print("Trade (0)");
          out.print("Look Around (1)");
          out.print("Set Sail (2)");
          action = Action.CHOOSEACTION;
          break;
        }
        case CHOOSEACTION: {
          Function<String, Action> basic = a -> {
            out.print("Choose the number of the action you wish to perform.");
            return Action.CHOOSEACTION;
          };
          action = getAnswer(basic, a -> {
            return Action.TRADE;
          }, b -> {
            return Action.LOOKAROUND;
          }, c -> {
            return Action.ATSEA;
          });
          break;
        }
        case LOOKAROUND: {
          switch (game.getIslandName()) {
            case "Dragon Cove":
              out.print("You find the shipyard.");
              out.print("Your current ship:");
              out.print("\t" + game.shipDescription());
              action = Action.CONFIRMBUYSHIP;
              break;
            default: {
              if (game.crewMemberAvailable()) {
                out.print("You find a " + game.getCrewName());
                action = Action.HIRECREW;
              } else {
                out.print("You find nothing.");
                action = Action.ONLAND;
              }
            }
          }
          break;
        }
        case CONFIRMBUYSHIP: {
          out.print("Do you want to purchase a new ship?");
          Supplier<Action> yes = () -> {
            return Action.BUYSHIP;
          };
          action = getAnswer(yes, Action.ONLAND, Action.CONFIRMBUYSHIP);
          break;
        } 
        case HIRECREW: {
          out.print("Would you like to hire a " + game.getCrewName()
              + " for D" + game.getCrewCost());
          Supplier<Action> yes = () -> {
            out.print(game.getCrewName() + " has joined your crew.");
            game.hireCrew();
            return Action.ONLAND;
          };
          action = getAnswer(yes, Action.ONLAND, Action.HIRECREW);
          break;
        }
        case BUYSHIP: {
          List<String> ships = game.shipDescriptions();
          for (int i = 0; i < ships.size(); i++) {
            out.print("\t" + ships.get(i) + " (" + i + ")");
          }
          out.print("\tToo Expensive (" + ships.size() + ")");
          action = Action.CHOOSESHIP;
          break;
        }
        case CHOOSESHIP: {
          Function<String, Action> basic = a -> {
            out.print("Choose the number of the ship you want to purchase.");
            return Action.CHOOSESHIP;
          };
          Function<String, Action> other = a -> {
            try {
              int b = Integer.valueOf(a);
              if (b < game.numShip()) {
                game.buyShip(b);
              }
              return Action.ONLAND;
            } catch (NumberFormatException e) {
              return basic.apply(a);
            }
          };
          action = getAnswer(basic, other, game.numShip() + 1);
          break;
        }
        case TRADE: {
          out.print("The prices for buying and selling on this island are :");
          out.print("\tGrain = D" + game.islandGrain() + "\tSpices = D" + game.islandSpice());
          out.print("\tMedicine = D" + game.islandMedicine() + "\tSilk = D" + game.islandSilk());
          out.print("Do you wish to purchase (0) or sell (1)?");
          action = Action.CHOOSETRADE;
          break;
        }
        case CHOOSETRADE: {
          action = getAnswer(basic -> {
            out.print("Choose the number of the trade you wish to perform.");
            return Action.CHOOSETRADE;
          }, purchase -> {
            out.print("Doubloons = D" + game.wallet() + "\tCargo Space = " 
                + game.cargoSpace() + "/" + game.cargoTotal());
            out.print("What do you wish to buy?");
            return Action.CHOOSEITEMBUY;
          }, sell -> {
            out.print("Grain = " + game.costItem(1, 0) + "\tSpices = " + game.costItem(1, 1));
            out.print("Medicine = " + game.costItem(1, 2) + "\tSilk = " + game.costItem(1, 3));
            out.print("What do you wish to sell?");
            return Action.CHOOSEITEMSELL;
          });
          break;
        }
        case CHOOSEITEMBUY: {
          Function<String, Action> other = a -> {
            savedNumber.add(Integer.valueOf(a));
            return Action.PURCHASE;
          };
          Function<String, Action> basic = a -> {
            out.print("Write the name of the item.");
            return Action.CHOOSEITEMBUY;
          };
          action = getAnswer(basic, other, 4);
          break;
        }
        case CHOOSEITEMSELL: {
          Function<String, Action> basic = a -> {
            out.print("Write the name of the item.");
            return Action.CHOOSEITEMSELL;
          };
          Function<String, Action> other = a -> {
            try {
              savedNumber.add(Integer.valueOf(a));
              return Action.SELL;
            } catch (NumberFormatException e) {
              return basic.apply(a);
            }
          };
          action = getAnswer(basic, other, 4);
          break;
        }
        case PURCHASE: {
          int index = savedNumber.remove(0);
          Function<Integer, Action> number = a -> {
            int ans = Math.min(a, game.getMaxItem(index));
            out.print("Buying " + ans + " " + Populate.item(index) + " for D" 
                + game.costItem(ans, index));
            savedNumber.add(ans);
            savedNumber.add(index);
            return Action.CONFIRMPURCHASE;
          };
          Function<String, Action> string = a -> {
            if (a.equals("max")) {
              return number.apply(Integer.MAX_VALUE);
            }
            out.print("Choose either a whole number or max.");
            return Action.PURCHASE;
          };
          action = getAnswer(number, string);
          break;
        }
        case CONFIRMPURCHASE: {
          int number = savedNumber.remove(0);
          int index = savedNumber.remove(0);
          out.print("Confirm?");
          Supplier<Action> yes = () -> {
            game.buyItem(number, index);
            out.print("Thank you for your purchase.");
            return Action.ONLAND;
          };
          action = getAnswer(yes, Action.ONLAND, Action.CONFIRMPURCHASE);
          break;
        }
        case SELL: {
          int index = savedNumber.remove(0);
          Function<Integer, Action> number = a -> {
            int ans = Math.min(a, game.captainItem(index));
            out.print("Selling " + ans + " " + Populate.item(index) + " for D" 
                + game.costItem(ans, index));
            savedNumber.add(ans);
            savedNumber.add(index);
            return Action.CONFIRMSELL;
          };
          Function<String, Action> string = a -> {
            if (a.equals("max")) {
              return number.apply(Integer.MAX_VALUE);
            }
            out.print("Choose either a whole number or max.");
            return Action.SELL;
          };
          action = getAnswer(number, string);
          break;
        }
        case CONFIRMSELL: {
          int number = savedNumber.remove(0);
          int index = savedNumber.remove(0);
          out.print("Confirm?");
          Supplier<Action> yes = () -> {
            game.sellItem(number, index);
            out.print("Thank you for your sale.");
            return Action.ONLAND;
          };
          action = getAnswer(yes, Action.ONLAND, Action.CONFIRMSELL);
          break;
        }
        case QUIT: {
          out.print("Thank you for playing.");
          running = false;
          break;
        }
        default: {
          action = Action.QUIT;
        }
      }
    }
  }





//  private void startJourney() {
//    if (game.atSea()) {
//      out.print("You are sailing at " + game.getSpeed() + " knots "
//          + "through the clear waters.");
//      out.print("Where do you want to head?");
//      List<String> islands = game.getIslandNames();
//      for (int i = 0; i < islands.size(); i++) {
//        out.print("\t" + islands.get(i) + " (" + i + ")");
//      }
//      chooseIsland();
//    }
//  }
//
//  private void chooseIsland() {
//    String answer = user.read();
//    switch (answer) {
//      case "0": 
//        travelTo(0);
//        break;
//      case "1": 
//        travelTo(1);
//        break;
//      case "2": 
//        travelTo(2);
//        break;
//      case "3": 
//        travelTo(3);
//        break;
//      case "4": 
//        travelTo(4);
//        break;
//      case "quit":
//        System.exit(0);
//        break;
//      default:
//        out.print("Please enter the number of the island you want to travel to.");
//        chooseIsland();
//    }
//  }
//
//  private void travelTo(int index) {
//    game.travelTo(index);
//    out.print("You have arrived at " + game.getIslandName());
//
//    boolean onLand = true;
//    while (onLand) { 
//      out.print("What do you want to do?");
//      out.print("Trade (0)");
//      out.print("Look Around (1)");
//      out.print("Set Sail (2)");
//      onLand = chooseAction();
//    }
//  }
//
//  private boolean chooseAction() {
//    String answer = user.read();
//    switch (answer) {
//      case "0": 
//        trade();
//        return true;
//      case "1": 
//        lookAround();
//        return true;
//      case "2":
//        startJourney();
//        return false;
//      case "quit":
//        System.exit(0);
//        return false;
//      default:
//        out.print("Choose the number of the action you wish to perform.");
//        return chooseAction();
//    }
//  }
//
//  private void lookAround() {
//    switch (game.getIslandName()) {
//      case "Dragon Cove": 
//        out.print("You find the shipyard.");
//        out.print("Your current ship:");
//        out.print("\t" + game.shipDescription());
//        if (confirm("Do you want to purchase a new ship?")) {
//          buyShip();
//        }
//        break;
//      default:
//        if (game.crewMemberAvailable()) {
//          out.print("You find a " + game.getCrewName());
//          if (confirm("Would you like to hire them for " + game.getCrewCost())) {
//            out.print(game.getCrewName() + " has joined your crew.");
//            game.hireCrew();
//          }
//        } else {
//          out.print("You find nothing.");
//        }
//    }
//  }
//
//  private void buyShip() {
//    List<String> ships = game.shipDescriptions();
//    for (int i = 0; i < ships.size(); i++) {
//      out.print("\t" + ships.get(i) + " (" + i + ")");
//    }
//    out.print("\tToo Expensive (" + ships.size() + ")");
//    chooseShip(ships.size());
//  }
//
//  private void chooseShip(int size) {
//    String answer = user.read();
//    if (answer.equals("quit")) {
//      System.exit(0);
//    }
//    try {
//      int index = Integer.valueOf(answer);
//      if (index >= 0 && index < size) {
//        game.buyShip(index);
//      } else if (index == size) {
//        return;
//      } else {
//        throw new NumberFormatException();
//      }
//    } catch (NumberFormatException e) {
//      out.print("Choose the number of the ship you want to purchase.");
//      chooseShip(size);
//    }
//  }
//
//  private void trade() {
//    out.print("The prices for buying and selling on this island are :");
//    out.print("\tGrain = D" + game.islandGrain() + "\tSpices = D" + game.islandSpice());
//    out.print("\tMedicine = D" + game.islandMedicine() + "\tSilk = D" + game.islandSilk());
//    out.print("Do you wish to purchase (0) or sell (1)?");
//    chooseTrade();
//  }
//
//  private void chooseTrade() {
//    String answer = user.read();
//    switch (answer) {
//      case "0": {
//        out.print("Doubloons = D" + game.wallet() + "\tCargo Space = " 
//            + game.cargoSpace() + "/" + game.cargoTotal());
//        out.print("What do you wish to buy?");
//        int index = chooseItem();
//        out.print("How many do you want to buy?");
//        purchase(index);
//        break;
//      }
//      case "1": {
//        out.print("Grain = " + game.costItem(1, 0) + "\tSpices = " + game.costItem(1, 1));
//        out.print("Medicine = " + game.costItem(1, 2) + "\tSilk = " + game.costItem(1, 3));
//        out.print("What do you wish to sell?");
//        int index = chooseItem();
//        out.print("How many do you want to sell?");
//        sell(index);
//        break;
//      }
//      case "quit":
//        System.exit(0);
//        break;
//      default:
//        out.print("Choose the number of the trade you wish to perform.");
//        chooseTrade();
//    }
//  }
//
//  private int chooseItem() {
//    String answer = user.read().toLowerCase();
//    switch (answer) {
//      case "grain":
//        return 0;
//      case "spices":
//        return 1;
//      case "medicine":
//        return 2;
//      case "silk":
//        return 3;
//      case "quit":
//        System.exit(0);
//        return -1;
//      default:
//        out.print("Write the name of the item.");
//        return chooseItem();
//    }
//  }
//
//  private void purchase(int index) {
//    String answer = user.read();
//    if (answer.equalsIgnoreCase("max")) {
//      int max = game.getMaxItem(index);
//      out.print("Buying " + max + " " + Populate.item(index) + " for D" 
//          + game.costItem(max, index));
//      if (confirm()) {
//        game.buyItem(max, index);
//        out.print("Thank you for your purchase.");
//      }
//    } else if (answer.equals("quit")) {
//      System.exit(0);
//    } else {
//      try {
//        int ans = Math.min(Integer.valueOf(answer), game.getMaxItem(index));
//        out.print("Buying " + ans + " " + Populate.item(index) + " for D" 
//            + game.costItem(ans, index));
//        if (confirm()) {
//          game.buyItem(ans, index);
//          out.print("Thank you for your purchase.");
//        }
//      } catch (NumberFormatException e) {
//        out.print("Choose either a whole number or max.");
//      }
//    }
//  }
//
//  private boolean confirm() {
//    return confirm("Confirm?");
//  }
//
//  private boolean confirm(String message) {
//    out.print(message);
//    String answer = user.read();
//    if (answer.equalsIgnoreCase("yes")) {
//      return true;
//    } else if (answer.equalsIgnoreCase("no")) {
//      return false;
//    } else if (answer.equals("quit")) {
//      System.exit(0);
//      return false;
//    } else {
//      return confirm(message);
//    }
//  }
//
//  private void sell(int index) {
//    String answer = user.read();
//    if (answer.equalsIgnoreCase("max")) {
//      int max = game.captainItem(index);
//      out.print("Selling " + max + " " + Populate.item(index) + " for D" 
//          + game.costItem(max, index));
//      if (confirm()) {
//        game.sellItem(max, index);
//        out.print("Thank you for your sale.");
//      }
//    } else if (answer.equals("quit")) {
//      System.exit(0);
//    } else {
//      try {
//        int ans = Math.min(Integer.valueOf(answer), game.getMaxItem(index));
//        out.print("Selling " + ans + " " + Populate.item(index) + " for D" 
//            + game.costItem(ans, index));
//        if (confirm()) {
//          game.sellItem(ans, index);
//          out.print("Thank you for your sale.");
//        }
//      } catch (NumberFormatException e) {
//        out.print("Choose either a whole number or max.");
//      }
//    }
//  }

  @SafeVarargs
  private Action getAnswer(Function<String, Action> basic, Function<String, Action>... results) {
    return getAnswer(answer -> {
      for (int i = 0; i < results.length; i++) {
        if (answer.equals(i + "")) {
          return results[i].apply(answer);
        }
      }
      return basic.apply(answer);
    });
  }

  private Action getAnswer(Function<String, Action> basic, 
      Function<String, Action> other, int options) {
    return getAnswer(answer -> {
      for (int i = 0; i < options; i++) {
        if (answer.equals(i + "")) {
          return other.apply(answer);
        }
      }
      return basic.apply(answer);
    });
  }
  
  private Action getAnswer(Function<Integer, Action> number, Function<String, Action> string) {
    return getAnswer(answer -> {
      try {
        int integer = Integer.valueOf(answer);
        return number.apply(integer);
      } catch (NumberFormatException e) {
        return string.apply(answer);
      }
    });
  }
  
  private Action getAnswer(Supplier<Action> yes, Action no, Action other) {
    return getAnswer(answer -> {
      if (answer.equals("yes")) {
        return yes.get();
      } else if (answer.equals("no")) {
        return no;
      }
      return other;
    });
  }
  
  private Action getAnswer(Function<String, Action> action) {
    String answer = user.read();
    if (answer.equals("quit")) {
      return Action.QUIT;
    }
    return action.apply(answer);
  }

}
