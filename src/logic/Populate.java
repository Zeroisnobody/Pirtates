package logic;

/**
 * Populate with knowledge.

 * @author Bryony
 *
 */
public class Populate {

  /**
   * An array of islands.

   * @return an array of islands
   */
  public Island[] islands() {
    Island[] lands = new Island[5];
    
    lands[0] =  new Island("Dragon Cove", 28, 34, 23, 14);
    lands[1] =  new Island("Bouffant Bay", 28, 23, 16, 36);
    lands[2] =  new Island("Pirate Outpost", 40, 17, 29, 25);
    lands[3] =  new Island("Parrot Port", 25, 25, 38, 25);
    lands[4] =  new Island("Golden Harbor", 12, 31, 30, 29);
    
    return lands;
  }
  
  protected String homeLand() {
    return "Fort Ridley";
  }
  
  public Ship raft = new Ship("Raft", 0, 20, 6, 30, 0);
  public Ship jimmyRigger = new Ship("Jimmy Rigger", 3000, 60, 7, 50, 1);
  public Ship carabelle = new Ship("Carabelle", 9000, 200, 8, 70, 1);
  public Ship seaSultan = new Ship("Sea Sultan", 30000, 600, 9, 90, 2);
  public Ship koi = new Ship("The Koi", 90000, 1800, 12, 100, 3);
  public Ship steamingFury = new Ship("Steaming Fury", 300000, 5000, 12, 140, 4);
  public Ship phoenixWarbird = new Ship("Phoenix Warbird", 1000000, 6000, 13, 180, 5);
  
  /**
   * An array of Ships.

   * @return A list of ships
   */
  public Ship[] ships() {
    Ship[] ships = {
        raft, jimmyRigger, carabelle, seaSultan, koi, steamingFury, phoenixWarbird
    };
    return ships;
  }
  
  public Crew cargoMaster = new Crew("Cargo Master", 15000);
  
  protected Crew[] crew() {
    Crew[] crew = new Crew[6];
    
    crew[0] = new Crew("Cabin Boy", 0);
    crew[1] = new Crew("Shipwright", 20000);
    crew[2] = cargoMaster;
    crew[3] = new Crew("Navigator", 12000);
    crew[4] = new Crew("Cannoneer", 20000);
    crew[5] = new Crew("Cook", 0);
    
    return crew;
  }

  /**
   * Return the name of the item.

   * @param index The index of the item
   * @return The item's name
   */
  public String item(int index) {
    switch (index) {
      case 0: return "Grain";
      case 1: return "Spices";
      case 2: return "Medicine";
      case 3: return "Silk";
      default: return "";
    }
  }
  
}
