package logic.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import logic.Captain;
import logic.Crew;
import logic.Populate;
import org.junit.jupiter.api.Test;

class TestCaptain {

  @Test
  void test() {
    Populate populate = new Populate();
    Captain player = new Captain(Crew.empty, Crew.empty, populate.raft);
    // Player should start with no money
    assertEquals(player.doubloons(), 0);
    // If Player doesn't have money, don't buy ship.
    player.buyShip(populate.jimmyRigger);
    assertEquals(player.shipDescription(), populate.raft.toStringOwned());
    int cost = 10000;
    // Player selling should increase money and reduce stock.
    // Player shouldn't sell more than they own.
    assertEquals(player.cargo(), 13);
    player.sell(0, cost, 20);
    assertEquals(player.doubloons(), 40000);
    assertEquals(player.getSellMax(0), 0);
    assertEquals(player.cargo(), 17);
    // Player buying ship should reduce money.
    // Ship should change to brought ship.
    player.buyShip(populate.jimmyRigger);
    assertEquals(player.doubloons(), 37000);
    assertEquals(player.shipDescription(), populate.jimmyRigger.toStringOwned());
    assertEquals(player.cargo(), 57);
    // Player hiring crew shouldn't change ship.
    // This is done in Archipelago.
    assertEquals(player.cargoTotal(), 60);
    player.addMember(populate.cargoMaster);
    assertEquals(player.cargoTotal(), 60);
    // Player buying shouldn't buy more than cargo.
    cost = 1;
    assertEquals(player.getBuyMax(cost), player.cargo());
    player.buy(0, 1, 100);
    assertEquals(player.cargo(), 0);
    assertEquals(player.getSellMax(0), 57);
    // Check others work
    assertEquals(player.speed(), populate.jimmyRigger.getSpeed());
    assertTrue(player.getHit());
    assertTrue(populate.jimmyRigger.stillAlive());
    while (player.getHit()) {}
    assertFalse(populate.jimmyRigger.stillAlive());
    player.heal();
    assertFalse(populate.jimmyRigger.stillAlive());
    player.dock();
    assertTrue(populate.jimmyRigger.stillAlive());
  }

}
