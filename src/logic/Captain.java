package logic;

import java.util.ArrayList;

/**
 * The actions of the captain.

 * @author Bryony
 *
 */
public class Captain {

  private ArrayList<Crew> crew = new ArrayList<>();
  private Ship ship;
  private int doubloons;
  private Cargo cargo;
  
  /**
   * Captain sets off.

   * @param cook The cook
   * @param cabinBoy The cabin boy
   * @param raft The original ship
   */
  public Captain(Crew cook, Crew cabinBoy, Ship raft) {
    crew.add(cabinBoy);
    crew.add(cook);
    ship = raft;
    doubloons = 0;
    cargo = new Cargo(4, 2, 1, 0);
  }
  
  public int speed() {
    return ship.getSpeed();
  }
  
  public boolean getHit() {
    ship.getHit();
    return ship.stillAlive();
  }
  
  public void heal() {
    ship.heal();
  }
  
  public int doubloons() {
    return doubloons;
  }
  
  /**
   * Buy the given ship and trade in old ship if the Captain has enough money.

   * @param newShip The new ship to buy
   * @return Returns true if the ship was brought
   */
  public boolean buyShip(Ship newShip) {
    if (newShip.getPrice() - ship.getTradein() <= doubloons) {
      doubloons -= newShip.getPrice() - ship.getTradein();
      ship = newShip;
      return true;
    }
    return false;
  }
  
  /**
   * Returns the max amount of an item the Captain can buy.

   * @param price The price of the item
   * @return How much the Captain should buy
   */
  public int getBuyMax(int price) {
    int maxNum = ship.getCargo() - cargo.total();
    int maxPrice = (int) (Math.rint(Double.valueOf(doubloons) / Double.valueOf(price)));
    return Math.max(maxNum, maxPrice);
  }
  
  public void buyGrain(int price, int num) {
    buy(0, price, num);
  }
  
  public void buySpice(int price, int num) {
    buy(1, price, num);
  }
  
  public void buyMedicine(int price, int num) {
    buy(2, price, num);
  }
  
  public void buySilk(int price, int num) {
    buy(3, price, num);
  }
  
  private void buy(int index, int price, int num) {
    int amount = Math.min(getBuyMax(price), num);
    int cost = amount * price;
    cargo.buy(index, amount);
    doubloons -= cost;
  }
  
  public int getSellMaxGrain() {
    return cargo.get(0);
  }
  
  public int getSellMaxSpice() {
    return cargo.get(1);
  }
  
  public int getSellMaxMedicine() {
    return cargo.get(2);
  }
  
  public int getSellMaxSilk() {
    return cargo.get(3);
  }
  
  public void sellGrain(int price, int num) {
    sell(0, price, num);
  }
  
  public void sellSpice(int price, int num) {
    sell(1, price, num);
  }
  
  public void sellMedicine(int price, int num) {
    sell(2, price, num);
  }
  
  public void sellSilk(int price, int num) {
    sell(3, price, num);
  }
  
  private void sell(int index, int price, int num) {
    int amount = Math.min(cargo.get(index), num);
    int cost = amount * price;
    cargo.sell(index, amount);
    doubloons += cost;
  }
  
}
