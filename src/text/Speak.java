package text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import logic.Archipelago;
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
  public Speak(Archipelago game, Input user, Output out) {
    this.game = game;
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

  protected void setSail() {
    out.print("Set Sail?");
    Action action = Action.ONLAND;
    while (action == Action.ONLAND) {
      action = getAnswer(() -> {
        game.setSail();
        rounds();
        return Action.QUIT;
      }, Action.QUIT, Action.ONLAND);
    }
  }

  /**
   * The possible states the game can be in.

   * @author Bryony
   *
   */
  protected enum Action {
    ATSEA,
    CHOOSEISLAND,
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

  protected void rounds() {
    boolean running = true;
    Action action = Action.ATSEA;
    ArrayList<Integer> savedNumber = new ArrayList<>();
    while (running) {
      switch (action) {
        case ATSEA: {
          action = atSea();
          break;
        }
        case CHOOSEISLAND: {
          action = chooseIsland();
          break;
        }
        case ONLAND: {
          action = onLand();
          break;
        }
        case CHOOSEACTION: {
          action = chooseAction();
          break;
        }
        case LOOKAROUND: {
          action = lookAround();
          break;
        }
        case CONFIRMBUYSHIP: {
          action = confirmBuyShip();
          break;
        } 
        case HIRECREW: {
          action = hireCrew();
          break;
        }
        case BUYSHIP: {
          action = buyShip();
          break;
        }
        case CHOOSESHIP: {
          action = chooseShip();
          break;
        }
        case TRADE: {
          action = trade();
          break;
        }
        case CHOOSETRADE: {
          action = chooseTrade();
          break;
        }
        case CHOOSEITEMBUY: {
          action = chooseItemBuy(savedNumber);
          break;
        }
        case CHOOSEITEMSELL: {
          action = chooseItemSell(savedNumber);
          break;
        }
        case PURCHASE: {
          action = purchase(savedNumber);
          break;
        }
        case CONFIRMPURCHASE: {
          action = confirmPurchase(savedNumber);
          break;
        }
        case SELL: {
          action = sell(savedNumber);
          break;
        }
        case CONFIRMSELL: {
          action = confirmSell(savedNumber);
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

  protected Action atSea() {
    out.print("You are sailing at " + game.getSpeed() + " knots "
        + "through the clear waters.");
    out.print("Where do you want to head?");
    List<String> islands = game.getIslandNames();
    for (int i = 0; i < islands.size(); i++) {
      out.print("\t" + islands.get(i) + " (" + i + ")");
    }
    return Action.CHOOSEISLAND;
  }

  protected Action chooseIsland() {
    Function<String, Action> basic = a -> {
      out.print("Please enter the "
          + "number of the island you want to travel to.");
      return Action.CHOOSEISLAND;
    };
    Function<String, Action> other = a -> {
      game.travelTo(Integer.valueOf(a));
      out.print("You have arrived at " + game.getIslandName());
      return Action.ONLAND;
    };
    return getAnswer(basic, other, 5);
  }

  protected Action onLand() {
    out.print("What do you want to do?");
    out.print("Trade (0)");
    out.print("Look Around (1)");
    out.print("Set Sail (2)");
    return Action.CHOOSEACTION;
  }

  protected Action chooseAction() {
    Function<String, Action> basic = a -> {
      out.print("Choose the number of the action you wish to perform.");
      return Action.CHOOSEACTION;
    };
    return getAnswer(basic, a -> {
      return Action.TRADE;
    }, b -> {
      return Action.LOOKAROUND;
    }, c -> {
      return Action.ATSEA;
    });
  }

  protected Action lookAround() {
    switch (game.getIslandName()) {
      case "Dragon Cove":
        out.print("You find the shipyard.");
        out.print("Your current ship:");
        out.print("\t" + game.shipDescription());
        return Action.CONFIRMBUYSHIP;
      default: {
        if (game.crewMemberAvailable()) {
          out.print("You find a " + game.getCrewName());
          return Action.HIRECREW;
        } else {
          out.print("You find nothing.");
          return Action.ONLAND;
        }
      }
    }
  }

  protected Action confirmBuyShip() {
    out.print("Do you want to purchase a new ship?");
    Supplier<Action> yes = () -> {
      return Action.BUYSHIP;
    };
    return getAnswer(yes, Action.ONLAND, Action.CONFIRMBUYSHIP);
  }

  protected Action hireCrew() {
    out.print("Would you like to hire a " + game.getCrewName() + " for D" + game.getCrewCost());
    Supplier<Action> yes = () -> {
      String name = game.getCrewName();
      if (game.hireCrew()) {
        out.print(name + " has joined your crew.");
      } else {
        out.print("You did not have enough money to hire the " + game.getCrewName());
      }
      return Action.ONLAND;
    };
    return getAnswer(yes, Action.ONLAND, Action.HIRECREW);
  }

  protected Action buyShip() {
    List<String> ships = game.shipDescriptions();
    for (int i = 0; i < ships.size(); i++) {
      out.print("\t" + ships.get(i) + " (" + i + ")");
    }
    out.print("\tToo Expensive (" + ships.size() + ")");
    return Action.CHOOSESHIP;
  }

  protected Action chooseShip() {
    Function<String, Action> basic = a -> {
      out.print("Choose the number of the ship you want to purchase.");
      return Action.CHOOSESHIP;
    };
    Function<String, Action> other = a -> {
      int b = Integer.valueOf(a);
      if (b < game.numShip()) {
        if (game.buyShip(b)) {
          out.print("Your new ship is waiting at the docks for you.");
        } else {
          out.print("You don't have enough money. Come back later.");
        }
      }
      return Action.ONLAND;
    };
    return getAnswer(basic, other, game.numShip() + 1);
  }

  protected Action trade() {
    out.print("The prices for buying and selling on this island are :");
    out.print("\tGrain = D" + game.islandGrain() + "\tSpices = D" + game.islandSpice());
    out.print("\tMedicine = D" + game.islandMedicine() + "\tSilk = D" + game.islandSilk());
    out.print("Do you wish to purchase (0) or sell (1)?");
    return Action.CHOOSETRADE;
  }

  protected Action chooseTrade() {
    return getAnswer(basic -> {
      out.print("Choose the number of the trade you wish to perform.");
      return Action.CHOOSETRADE;
    }, purchase -> {
      out.print("Doubloons = D" + game.wallet() + "\tCargo Space = " 
          + game.cargoSpace() + "/" + game.cargoTotal());
      out.print("What do you wish to buy?");
      return Action.CHOOSEITEMBUY;
    }, sell -> {
      out.print("(0) Grain = " + game.costItem(1, 0) + "\t(1) Spices = " + game.costItem(1, 1));
      out.print("(2) Medicine = " + game.costItem(1, 2) + "\t(3) Silk = " + game.costItem(1, 3));
      out.print("What do you wish to sell?");
      return Action.CHOOSEITEMSELL;
    });
  }

  protected Action chooseItemBuy(ArrayList<Integer> savedNumber) {
    Function<String, Action> other = a -> {
      savedNumber.add(Integer.valueOf(a));
      return Action.PURCHASE;
    };
    Function<String, Action> basic = a -> {
      if (a.equals("cancel")) {
        return Action.ONLAND;
      }
      out.print("Write the index of the item or cancel.");
      return Action.CHOOSEITEMBUY;
    };
    return getAnswer(basic, other, 4);
  }

  protected Action chooseItemSell(ArrayList<Integer> savedNumber) {
    Function<String, Action> basic = a -> {
      if (a.equals("cancel")) {
        return Action.ONLAND;
      }
      out.print("Write the index of the item or cancel.");
      return Action.CHOOSEITEMSELL;
    };
    Function<String, Action> other = a -> {
      savedNumber.add(Integer.valueOf(a));
      return Action.SELL;
    };
    return getAnswer(basic, other, 4);
  }

  protected Action purchase(ArrayList<Integer> savedNumber) {
    int index = savedNumber.remove(0);
    Function<Integer, Action> number = a -> {
      int ans = Math.min(a, game.getMaxItem(index));
      out.print("Buying " + ans + " " + game.populate().item(index) + " for D" 
          + game.costItem(ans, index));
      savedNumber.add(ans);
      savedNumber.add(index);
      return Action.CONFIRMPURCHASE;
    };
    Function<String, Action> string = a -> {
      if (a.equals("max")) {
        return number.apply(Integer.MAX_VALUE);
      } else if (a.equals("cancel")) {
        return Action.ONLAND;
      }
      out.print("Choose either a whole number, max, or cancel.");
      return Action.PURCHASE;
    };
    return getAnswer(number, string);
  }
  
  protected Action confirmPurchase(ArrayList<Integer> savedNumber) {
    int number = savedNumber.remove(0);
    int index = savedNumber.remove(0);
    out.print("Confirm?");
    Supplier<Action> yes = () -> {
      game.buyItem(number, index);
      out.print("Thank you for your purchase.");
      return Action.ONLAND;
    };
    return getAnswer(yes, Action.ONLAND, Action.CONFIRMPURCHASE);
  }
  
  protected Action sell(ArrayList<Integer> savedNumber) {
    int index = savedNumber.remove(0);
    Function<Integer, Action> number = a -> {
      int ans = Math.min(a, game.captainItem(index));
      out.print("Selling " + ans + " " + game.populate().item(index) + " for D" 
          + game.costItem(ans, index));
      savedNumber.add(ans);
      savedNumber.add(index);
      return Action.CONFIRMSELL;
    };
    Function<String, Action> string = a -> {
      if (a.equals("max")) {
        return number.apply(Integer.MAX_VALUE);
      } else if (a.equals("cancel")) {
        return Action.ONLAND;
      }
      out.print("Choose either a whole number, max, or cancel.");
      return Action.SELL;
    };
    return getAnswer(number, string);
  }
  
  protected Action confirmSell(ArrayList<Integer> savedNumber) {
    int number = savedNumber.remove(0);
    int index = savedNumber.remove(0);
    out.print("Confirm?");
    Supplier<Action> yes = () -> {
      game.sellItem(number, index);
      out.print("Thank you for your sale.");
      return Action.ONLAND;
    };
    return getAnswer(yes, Action.ONLAND, Action.CONFIRMSELL);
  }

  @SafeVarargs
  protected final Action getAnswer(Function<String, Action> basic, 
      Function<String, Action>... results) {
    return getAnswer(answer -> {
      for (int i = 0; i < results.length; i++) {
        if (answer.equals(i + "")) {
          return results[i].apply(answer);
        }
      }
      return basic.apply(answer);
    });
  }

  protected Action getAnswer(Function<String, Action> basic, 
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

  protected Action getAnswer(Function<Integer, Action> number, Function<String, Action> string) {
    return getAnswer(answer -> {
      try {
        int integer = Integer.valueOf(answer);
        return number.apply(integer);
      } catch (NumberFormatException e) {
        return string.apply(answer);
      }
    });
  }

  protected Action getAnswer(Supplier<Action> yes, Action no, Action other) {
    return getAnswer(answer -> {
      if (answer.equals("yes")) {
        return yes.get();
      } else if (answer.equals("no")) {
        return no;
      }
      return other;
    });
  }

  protected Action getAnswer(Function<String, Action> action) {
    String answer = user.read();
    if (answer.equals("quit")) {
      return Action.QUIT;
    }
    return action.apply(answer);
  }

}
