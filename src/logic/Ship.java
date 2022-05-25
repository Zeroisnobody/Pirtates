package logic;

/**
 * Holds the information for a ship.

 * @author Bryony
 *
 */
public class Ship {

  private String name;
  private int price;
  private int cargo;
  private int speed;
  private int life;
  private int firepower;
  
  private boolean cargoMaster = false;
  private boolean navigator = false;
  private boolean shipwright = false;
  private int lifeLeft;
  
  /**
   * Create a ship.

   * @param name Ship's name
   * @param price The price of the ship
   * @param cargo The amount of cargo the ship can carry
   * @param speed The speed of the ship
   * @param life The life of the ship
   * @param firepower The number of cannons the ship has
   */
  protected Ship(String name, int price, int cargo, int speed, 
      int life, int firepower) {
    this.name = name;
    this.price = price;
    this.cargo = cargo;
    this.speed = speed;
    this.life = life;
    this.lifeLeft = life;
    this.firepower = firepower;
  }

  public String getName() {
    return name;
  }

  public int getPrice() {
    return price;
  }

  /**
   * The amount of items that can be stored in the cargo.
   * Is increased by the Cargo Master.

   * @return The cargo size
   */
  public int getCargo() {
    if (cargoMaster) {
      return (int) (cargo * 1.5);
    }
    return cargo;
  }

  /**
   * The speed of the ship.
   * Is increased by the Navigator.

   * @return The speed of the ship.
   */
  public int getSpeed() {
    if (navigator) {
      return (int) (speed + 2);
    }
    return speed;
  }

  public int getLife() {
    return life;
  }

  public int getFirepower() {
    return firepower;
  }

  public int getTradein() {
    return (int) (price * 0.25);
  }
  
  public void addCargoMaster() {
    cargoMaster = true;
  }
  
  public void addNavigator() {
    navigator = true;
  }
  
  public void addShipwright() {
    shipwright = true;
  }
  
  public void getHit() {
    lifeLeft -= 20;
  }
  
  /**
   * The shipwright can fix the ship mid journey.
   */
  public void heal() {
    if (shipwright) {
      lifeLeft += 10;
    }
  }
  
  public void fix() {
    lifeLeft = life;
  }
  
  public boolean stillAlive() {
    return lifeLeft > 0;
  }
  
  public String toString() {
    return name + ":\tPrice = D" + price + "\tCargo = " + cargo;
  }
  
}
