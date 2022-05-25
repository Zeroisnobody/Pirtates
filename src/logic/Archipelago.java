package logic;

import java.util.Arrays;
import java.util.List;

/**
 * The board game.

 * @author Bryony
 *
 */
public class Archipelago {

  private Crew[] possibleMembers = Populate.crew();
  private Island[] islands = Populate.islands();
  private Ship[] ships = Populate.ships();

  private Captain player;
  private int currentLand;
  private boolean atSea;

  /**
   * Create an Archipelago.
   */
  public Archipelago() {
    player = new Captain(possibleMembers[0], possibleMembers[5], ships[0]);
    possibleMembers[0] = null;
    possibleMembers[1] = null;
    currentLand = -1;
    atSea = false;
  }

  /**
   * Get the name of the current land (or sea).

   * @return The name of the island
   */
  public String getIslandName() {
    if (currentLand == -1) {
      if (!atSea) {
        return Populate.homeLand();
      }
      return "The Sea";
    }
    return islands[currentLand].getName();
  }

  /**
   * Set sail.
   */
  public void setSail() {
    currentLand = -1;
    atSea = true;
  }

  public boolean atSea() {
    return atSea();
  }

  public int getSpeed() {
    return player.speed();
  }

  public List<String> getIslandNames() {
    return Arrays.asList(islands).stream().map(i -> i.getName()).toList();
  }

  public void travelTo(int index) {
    currentLand = index;
    atSea = false;
  }

  public int islandGrain() {
    return islands[currentLand].getGrain();
  }

  public int islandSpice() {
    return islands[currentLand].getSpice();
  }

  public int islandMedicine() {
    return islands[currentLand].getMedicine();
  }

  public int islandSilk() {
    return islands[currentLand].getSilk();
  }

  public int wallet() {
    return player.doubloons();
  }

  public int cargoSpace() {
    return player.cargo();
  }

  public int cargoTotal() {
    return player.cargoTotal();
  }

  public int getMaxItem(int index) {
    return player.getBuyMax(islands[currentLand].get(index));
  }

  /**
   * Buy a number of items.

   * @param number The number of items to purchase
   * @param index The index of the item
   */
  public void buyItem(int number, int index) {
    player.buy(index, islands[currentLand].get(index), number);
  }
  
  /**
   * Sell a number of items.

   * @param number The number of items to sell
   * @param index The index of the item
   */
  public void sellItem(int number, int index) {
    player.sell(index, islands[currentLand].get(index), number);
  }
  
  public int costItem(int number, int index) {
    return islands[currentLand].get(index) * number;
  }
  
  public int captainItem(int index) {
    return player.getSellMax(index);
  }
  
  public boolean crewMemberAvailable() {
    return possibleMembers[currentLand] == null;
  }
  
  public String getCrewName() {
    return possibleMembers[currentLand].getName();
  }
  
  public int getCrewCost() {
    return possibleMembers[currentLand].getSalary();
  }

  /**
   * Hire the crew member on the current island.
   */
  public void hireCrew() {
    switch (possibleMembers[currentLand].getName()) {
      case "Cargo Master": 
        addCargoMaster();
        break;
      case "Navigator":
        addNavigator();
        break;
      case "Shipwright":
        addShipwright();
        break;
      default:
        break;
    }
    player.addMember(possibleMembers[currentLand]);
  }
  
  private void addCargoMaster() {
    for (Ship s : ships) {
      s.addCargoMaster();
    }
  }
  
  private void addNavigator() {
    for (Ship s : ships) {
      s.addNavigator();
    }
  }
  
  private void addShipwright() {
    for (Ship s : ships) {
      s.addShipwright();
    }
  }
  
  public List<String> shipDescriptions() {
    return Arrays.asList(ships).stream().map(s -> s.toString()).toList();
  }
  
  public String shipDescription() {
    return player.shipDescription();
  }
  
  public boolean buyShip(int index) {
    return player.buyShip(ships[index]);
  }
  
  public int numShip() {
    return ships.length;
  }
  
}
