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
  
}
