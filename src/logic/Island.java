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
  public Island(String name, int grain, int spice, int medicine, int silk) {
    this.name = name;
    this.grain = grain;
    this.spice = spice;
    this.medicine = medicine;
    this.silk = silk;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getGrain() {
    return grain;
  }

  public void setGrain(int grain) {
    this.grain = grain;
  }

  public int getSpice() {
    return spice;
  }

  public void setSpice(int spice) {
    this.spice = spice;
  }

  public int getMedicine() {
    return medicine;
  }

  public void setMedicine(int medicine) {
    this.medicine = medicine;
  }

  public int getSilk() {
    return silk;
  }

  public void setSilk(int silk) {
    this.silk = silk;
  }
  
}
