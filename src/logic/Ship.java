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
  
  /**
   * Create a ship.

   * @param name Ship's name
   * @param price The price of the ship
   * @param cargo The amount of cargo the ship can carry
   */
  protected Ship(String name, int price, int cargo, int speed, int life, int firepower) {
    this.name = name;
    this.price = price;
    this.cargo = cargo;
    this.speed = speed;
    this.life = life;
    this.firepower = firepower;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getCargo() {
    return cargo;
  }

  public void setCargo(int cargo) {
    this.cargo = cargo;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getLife() {
    return life;
  }

  public void setLife(int life) {
    this.life = life;
  }

  public int getFirepower() {
    return firepower;
  }

  public void setFirepower(int firepower) {
    this.firepower = firepower;
  }
  
}
