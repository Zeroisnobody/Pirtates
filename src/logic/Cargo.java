package logic;

/**
 * Description of the Cargo held in a ship.

 * @author Bryony
 *
 */
public class Cargo {

  private int grain;
  private int spice;
  private int medicine;
  private int silk;
  
  int total() {
    return grain + spice + medicine + silk;
  }
  
  /**
   * The Cargo.

   * @param g Grain
   * @param s Spice
   * @param m Medicine
   * @param l Silk
   */
  public Cargo(int g, int s, int m, int l) {
    grain = g;
    spice = s;
    medicine = m;
    silk = l;
  }
  
  /**
   * Buy an item.

   * @param index The item's index
   * @param amount Number of items
   */
  public void buy(int index, int amount) {
    switch (index) {
      case 0: grain += amount; 
        break;
      case 1: spice += amount; 
        break;
      case 2: medicine += amount; 
        break;
      case 3: silk += amount; 
        break;
      default: return;
    }
  }
  
  /**
   * Get the current amount of an item.

   * @param index The item index
   * @return The amount of the item
   */
  public int get(int index) {
    switch (index) {
      case 0: return grain;
      case 1: return spice;
      case 2: return medicine;
      case 3: return silk;
      default: return 0;
    }
  }
  
  void sell(int index, int amount) {
    buy(index, -amount);
  }
  
}
