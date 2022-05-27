package logic.test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import logic.Archipelago;
import logic.Populate;
import org.junit.jupiter.api.Test;

/**
 * Test the Archipelago Class.

 * @author Bryony
 *
 */
public class TestArchipelago {

  @Test
  void testStart() {
    Archipelago game = new Archipelago();
    assertEquals(game.getIslandName(), "Fort Ridley");
    assertFalse(game.atSea());
    game.travelTo(6);
    assertEquals(game.getIslandName(), "Fort Ridley");
    assertFalse(game.crewMemberAvailable());
    assertEquals(game.getCrewName(), "");
    assertEquals(game.getCrewCost(), 0);
  }
  
  @Test
  void testSetSail() {
    Archipelago game = new Archipelago();
    game.setSail();
    assertEquals(game.getIslandName(), "The Sea");
    assertTrue(game.atSea());
  }
  
  @Test
  void testDock() {
    Archipelago game = new Archipelago();
    game.setSail();
    game.travelTo(0);
    assertEquals(game.getIslandName(), "Dragon Cove");
    assertFalse(game.atSea());
    game.setSail();
    assertTrue(game.atSea());
    game.travelTo(4);
    assertEquals(game.getIslandName(), "Golden Harbor");
    assertFalse(game.atSea());
    game.setSail();
    game.travelTo(-1);
    assertTrue(game.atSea());
  }
  
  @Test
  void testMarket() {
    Archipelago game = new Archipelago();
    game.setSail();
    game.travelTo(0);
    assertEquals(game.costItem(1, 0), 28);
    assertEquals(game.costItem(1, 1), 34);
    assertEquals(game.costItem(1, 2), 23);
    assertEquals(game.costItem(1, 3), 14);
    game.setSail();
    game.travelTo(3);
    assertEquals(game.costItem(1, 0), 25);
    assertEquals(game.costItem(1, 1), 25);
    assertEquals(game.costItem(1, 2), 38);
    assertEquals(game.costItem(1, 3), 25);
  }
  
  @Test
  void testSell() {
    Archipelago game = new Archipelago();
    game.setSail();
    game.travelTo(0);
    assertEquals(game.captainItem(0), 4);
    assertEquals(game.captainItem(1), 2);
    assertEquals(game.captainItem(2), 1);
    assertEquals(game.captainItem(3), 0);
    assertEquals(game.wallet(), 0);
    game.sellItem(game.captainItem(0), 0);
    assertEquals(game.captainItem(0), 0);
    assertEquals(game.wallet(), 112); 
  }
  
  @Test
  void testBuyNoMoney() {
    Archipelago game = new Archipelago();
    game.setSail();
    game.travelTo(0);
    assertEquals(game.getMaxItem(0), 0);
    assertEquals(game.captainItem(0), 4);
    game.buyItem(10, 0);
    assertEquals(game.captainItem(0), 4);
  }
  
  @Test
  void testBuy() {
    Archipelago game = new Archipelago();
    game.setSail();
    game.travelTo(0);
    game.sellItem(4, 0);
    game.sellItem(2, 1);
    game.sellItem(1, 2);
    assertEquals(game.wallet(), 203);
    assertEquals(game.getMaxItem(3), 14);
    game.buyItem(14, 3);
    assertEquals(game.captainItem(3), 14);
    assertEquals(game.cargoSpace(), 6);
  }
  
  @Test
  void testShipNoMoney() {
    Archipelago game = new Archipelago();
    game.setSail();
    game.travelTo(0);
    assertEquals(game.shipDescription(), "Raft:\tTrade in = D0\tCargo = 20");
    game.buyShip(1);
    assertEquals(game.shipDescription(), "Raft:\tTrade in = D0\tCargo = 20");
  }
  
  @Test
  void testShip() {
    Archipelago game = new Archipelago();
    raise(game);
    raise(game);
    raise(game);
    assertEquals(game.wallet(), 3558);
    assertTrue(game.buyShip(1));
    assertEquals(game.wallet(), 558);
    assertEquals(game.shipDescription(), "Jimmy Rigger:\tTrade in = D750\tCargo = 60");
  }
  
  @Test
  void testCrew() {
    Archipelago game = new Archipelago();
    game.setSail();
    game.travelTo(3);
    game.hireCrew();
    assertTrue(game.crewMemberAvailable());
    assertEquals(game.getSpeed(), 6);
    for (int i = 0; i < 10; i++) {
      raise(game);
    }
    game.setSail();
    game.travelTo(3);
    game.hireCrew();
    assertFalse(game.crewMemberAvailable());
    assertEquals(game.wallet(), 2198);
    assertEquals(game.getSpeed(), 8);
  }
  
  @Test
  void testCrewLocation() {
    Archipelago game = new Archipelago();
    game.setSail();
    game.travelTo(0);
    assertEquals(game.getCrewName(), "");
    assertFalse(game.crewMemberAvailable());
    game.setSail();
    game.travelTo(1);
    assertEquals(game.getCrewName(), "Shipwright");
    assertTrue(game.crewMemberAvailable());
    game.setSail();
    game.travelTo(2);
    assertEquals(game.getCrewName(), "Cargo Master");
    assertTrue(game.crewMemberAvailable());
    game.setSail();
    game.travelTo(3);
    assertEquals(game.getCrewName(), "Navigator");
    assertTrue(game.crewMemberAvailable());
    game.setSail();
    game.travelTo(4);
    assertEquals(game.getCrewName(), "Cannoneer");
    assertTrue(game.crewMemberAvailable());
  }
  
  @Test
  void testCost() {
    Archipelago game = new Archipelago();
    game.setSail();
    game.travelTo(0);
    assertEquals(game.costItem(10, 3), 140);
  }
  
  @Test
  void testCrewCargoMaster() {
    Archipelago game = new Archipelago();
    assertEquals(game.cargoTotal(), 20);
    for (int i = 0; i < 11; i++) {
      raise(game);
    }
    game.setSail();
    game.travelTo(2);
    assertEquals(game.wallet(), 15718);
    game.hireCrew();
    assertEquals(game.wallet(), 718);
    assertEquals(game.cargoTotal(), 30);
  }
  
  @Test
  void testCrewShipWright() {
    Archipelago game = new Archipelago();
    assertEquals(game.cargoTotal(), 20);
    for (int i = 0; i < 20; i++) {
      raise(game);
    }
    game.setSail();
    game.travelTo(1);
    assertEquals(game.wallet(), 29398);
    game.hireCrew();
    assertEquals(game.wallet(), 9398);
  }
  
  @Test
  void testShips() {
    Archipelago game = new Archipelago();
    Populate p = game.populate();
    assertEquals(game.shipDescriptions().get(0), "Raft:\tPrice = D0\tCargo = 20");
    assertEquals(game.numShip(), p.ships().length);
  }

  /**
   * Do a round of islands to raise money.

   * @param game The current game
   */
  public static void raise(Archipelago game) {
    game.setSail();
    game.travelTo(0);
    game.sellItem(20, 1);
    game.buyItem(20, 3);
    game.setSail();
    game.travelTo(1);
    game.sellItem(20, 3);
    game.buyItem(20, 2);
    game.setSail();
    game.travelTo(3);
    game.sellItem(20, 2);
    game.buyItem(20, 0);
    game.setSail();
    game.travelTo(2);
    game.sellItem(20, 0);
    game.buyItem(20, 1);
  }

}
