package logic;

/**
 * Holds the information for an island.

 * @author Bryony
 *
 */
public class Island {

  private String name;
  private int grain;
  private int spice;
  private int medicine;
  private int silk;

  /**
   * Create an island.

   * @param name The island's name
   * @param grain The price of grain
   * @param spice The price of spice
   * @param medicine The price of medicine
   * @param silk The price of silk
   */
  protected Island(String name, int grain, int spice, int medicine, int silk) {
    this.name = name;
    this.grain = grain;
    this.spice = spice;
    this.medicine = medicine;
    this.silk = silk;
  }

  public String getName() {
    return name;
  }

  public int getGrain() {
    return grain;
  }

  public int getSpice() {
    return spice;
  }

  public int getMedicine() {
    return medicine;
  }

  public int getSilk() {
    return silk;
  }

  /**
   * Return the price of the item at the given index.

   * @param index The item's index
   * @return The price of the item
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

}
