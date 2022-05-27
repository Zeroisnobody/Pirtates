package logic.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import logic.Cargo;
import logic.Island;
import logic.Populate;
import logic.Ship;
import org.junit.jupiter.api.Test;

class TestOther {

  @Test
  void testIsland() {
    Populate p = new Populate();
    Island dc = p.islands()[0];
    assertEquals(dc.get(4), 0);
  }
  
  @Test
  void testCargo() {
    Cargo c = new Cargo(0, 0, 0, 0);
    c.buy(0, 20);
    assertEquals(c.get(0), 20);
    c.buy(-1, 20);
    assertEquals(c.get(0), 20);
    assertEquals(c.get(1), 0);
    assertEquals(c.get(2), 0);
    assertEquals(c.get(3), 0);
    assertEquals(c.get(-1), 0);
  }
  
  @Test
  void testShip() {
    Populate p = new Populate();
    Ship s = p.raft;
    assertEquals(s.getName(), "Raft");
    assertEquals(s.getLife(), 30);
    assertEquals(s.getFirepower(), 0);
    s.addShipwright();
    s.heal();
  }
  
  @Test
  void testPopulate() {
    Populate p = new Populate();
    assertEquals(p.item(0), "Grain");
    assertEquals(p.item(1), "Spices");
    assertEquals(p.item(2), "Medicine");
    assertEquals(p.item(3), "Silk");
    assertEquals(p.item(4), "");
  }

}
