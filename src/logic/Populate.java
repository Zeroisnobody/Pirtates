package logic;

/**
 * Populate with knowledge.

 * @author Bryony
 *
 */
public class Populate {

  protected static Island[] islands() {
    Island[] lands = new Island[5];
    
    lands[0] =  new Island("Dragon Cove", 28, 34, 23, 14);
    lands[1] =  new Island("Bouffant Bay", 28, 23, 16, 36);
    lands[2] =  new Island("Pirate Outpost", 40, 17, 29, 25);
    lands[3] =  new Island("Parrot Port", 25, 25, 38, 25);
    lands[4] =  new Island("Golden Harbor", 12, 31, 30, 29);
    
    return lands;
  }
  
  protected static String homeLand() {
    return "Fort Ridley";
  }
  
  protected static Ship[] ships() {
    Ship[] ships = new Ship[7];
    
    ships[0] = new Ship("Raft", 0, 20, 6, 30, 0);
    ships[1] = new Ship("Jimmy Rigger", 3000, 60, 7, 50, 1);
    ships[2] = new Ship("Carabelle", 9000, 200, 8, 70, 1);
    ships[3] = new Ship("Sea Sultan", 30000, 600, 9, 90, 2);
    ships[4] = new Ship("The Koi", 90000, 1800, 12, 100, 3);
    ships[5] = new Ship("Steaming Fury", 300000, 5000, 12, 140, 4);
    ships[6] = new Ship("Phoenix Warbird", 1000000, 6000, 13, 180, 5);
    
    return ships;
  }
  
  protected static Crew[] crew() {
    Crew[] crew = new Crew[6];
    
    crew[0] = new Crew("Cabin Boy", 0);
    crew[1] = new Crew("Shipwright", 20000);
    crew[2] = new Crew("Cargo Master", 15000);
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
  public static String item(int index) {
    switch (index) {
      case 0: return "Grain";
      case 1: return "Spices";
      case 2: return "Medicine";
      case 3: return "Silk";
      default: return "";
    }
  }
  
}
