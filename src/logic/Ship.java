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
  
  /**
   * Create a ship.

   * @param name Ship's name
   * @param price The price of the ship
   * @param cargo The amount of cargo the ship can carry
   */
  protected Ship(String name, int price, int cargo) {
    this.name = name;
    this.price = price;
    this.cargo = cargo;
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
  
}
