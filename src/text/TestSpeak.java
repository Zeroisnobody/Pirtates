package text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import logic.Archipelago;
import logic.test.TestArchipelago;
import org.junit.jupiter.api.Test;
import text.Speak.Action;
import text.TestText.TestInput;
import text.TestText.TestOutput;

class TestSpeak {

  @Test
  void testAtSea() {
    TestOutput out = new TestOutput();
    Speak s = new Speak(new Archipelago(), new TestInput("no"), out);
    assertEquals(s.atSea(), Action.CHOOSEISLAND);
  }
  
  @Test
  void testChooseIslandBasic() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "no"), out);
    assertEquals(s.chooseIsland(), Action.CHOOSEISLAND);
  }
  
  @Test
  void testChooseIslandOther() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "0"), out);
    assertEquals(s.chooseIsland(), Action.ONLAND);
    assertEquals(game.getIslandName(), "Dragon Cove");
  }
  
  @Test
  void testOnLand() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no"), out);
    assertEquals(s.onLand(), Action.CHOOSEACTION);
  }
  
  @Test
  void testChooseActionBasic() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "no"), out);
    assertEquals(s.chooseAction(), Action.CHOOSEACTION);
  }
  
  @Test
  void testChooseActionTrade() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "0"), out);
    assertEquals(s.chooseAction(), Action.TRADE);
  }
  
  @Test
  void testChooseActionLookAround() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "1"), out);
    assertEquals(s.chooseAction(), Action.LOOKAROUND);
  }
  
  @Test
  void testChooseActionAtSea() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "2"), out);
    assertEquals(s.chooseAction(), Action.ATSEA);
  }
  
  @Test
  void testLookAround() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no"), out);
    game.travelTo(0);
    assertEquals(s.lookAround(), Action.CONFIRMBUYSHIP);
    TestText.check(out, 5, "You find the shipyard.");
    game.travelTo(1);
    assertEquals(s.lookAround(), Action.HIRECREW);
    TestText.check(out, 8, "You find a Shipwright");
    for (int i = 0; i < 20; i++) {
      TestArchipelago.raise(game);
    }
    game.setSail();
    game.travelTo(1);
    game.hireCrew();
    assertEquals(s.lookAround(), Action.ONLAND);
    TestText.check(out, 9, "You find nothing.");
  }
  
  @Test
  void testConfirmBuyShip() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "yes", "no", "basic"), out);
    assertEquals(s.confirmBuyShip(), Action.BUYSHIP);
    assertEquals(s.confirmBuyShip(), Action.ONLAND);
    assertEquals(s.confirmBuyShip(), Action.CONFIRMBUYSHIP);
  }
  
  @Test
  void testHireCrew() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "yes", "yes", "no", "basic"), out);
    game.travelTo(1);
    assertEquals(s.hireCrew(), Action.ONLAND);
    assertTrue(game.crewMemberAvailable());
    TestText.check(out, 6, "You did not have enough money to hire the Shipwright");
    for (int i = 0; i < 20; i++) {
      TestArchipelago.raise(game);
    }
    game.setSail();
    game.travelTo(1);
    assertEquals(s.hireCrew(), Action.ONLAND);
    assertFalse(game.crewMemberAvailable());
    TestText.check(out, 8, "Shipwright has joined your crew.");
  }
  
  @Test
  void testBuyShip() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no"), out);
    assertEquals(s.buyShip(), Action.CHOOSESHIP);
  }
  
  @Test
  void testChooseShip() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "basic", "1", "1"), out);
    assertEquals(s.chooseShip(), Action.CHOOSESHIP);
    assertEquals(s.chooseShip(), Action.ONLAND);
    TestText.check(out, 6, "You don't have enough money. Come back later.");
    for (int i = 0; i < 20; i++) {
      TestArchipelago.raise(game);
    }
    assertEquals(s.chooseShip(), Action.ONLAND);
    TestText.check(out, 7, "Your new ship is waiting at the docks for you.");
    assertEquals(game.shipDescription(), "Jimmy Rigger:\tTrade in = D750\tCargo = 60");
  }
  
  @Test
  void testTrade() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no"), out);
    game.travelTo(0);
    assertEquals(s.trade(), Action.CHOOSETRADE);
  }
  
  @Test
  void testChooseTrade() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "basic", "0", "1"), out);
    game.travelTo(0);
    assertEquals(s.chooseTrade(), Action.CHOOSETRADE);
    assertEquals(s.chooseTrade(), Action.CHOOSEITEMBUY);
    assertEquals(s.chooseTrade(), Action.CHOOSEITEMSELL);
  }
  
  @Test
  void testChooseItemBuy() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "basic", "cancel", "1"), out);
    ArrayList<Integer> savedNumber = new ArrayList<>();
    assertEquals(s.chooseItemBuy(savedNumber), Action.CHOOSEITEMBUY);
    assertEquals(s.chooseItemBuy(savedNumber), Action.ONLAND);
    assertEquals(s.chooseItemBuy(savedNumber), Action.PURCHASE);
    assertEquals(savedNumber.get(0), 1);
  }
  
  @Test
  void testChooseItemSell() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "basic", "cancel", "1"), out);
    ArrayList<Integer> savedNumber = new ArrayList<>();
    assertEquals(s.chooseItemSell(savedNumber), Action.CHOOSEITEMSELL);
    assertEquals(s.chooseItemSell(savedNumber), Action.ONLAND);
    assertEquals(s.chooseItemSell(savedNumber), Action.SELL);
    assertEquals(savedNumber.get(0), 1);
  }
  
  @Test
  void testPurchase() {
    Archipelago game = new Archipelago();
    ArrayList<Integer> savedNumber = new ArrayList<>();
    savedNumber.add(0);
    for (int i = 0; i < 20; i++) {
      TestArchipelago.raise(game);
    }
    empty(game);
    game.travelTo(0);
    TestOutput out = new TestOutput();
    Speak s = new Speak(game, new TestInput("no", "basic", "cancel", "1", "max"), out);
    assertEquals(s.purchase(savedNumber), Action.PURCHASE);
    savedNumber.add(0);
    assertEquals(s.purchase(savedNumber), Action.ONLAND);
    savedNumber.add(0);
    assertEquals(s.purchase(savedNumber), Action.CONFIRMPURCHASE);
    assertEquals(savedNumber.remove(0), 1);
    assertEquals(savedNumber.remove(0), 0);
    savedNumber.add(0);
    assertEquals(s.purchase(savedNumber), Action.CONFIRMPURCHASE);
    assertEquals(savedNumber.remove(0), 20);
    assertEquals(savedNumber.remove(0), 0);
  }
  
  @Test
  void testConfirmPurchase() {
    Archipelago game = new Archipelago();
    ArrayList<Integer> savedNumber = new ArrayList<>();
    savedNumber.add(1);
    savedNumber.add(0);
    for (int i = 0; i < 20; i++) {
      TestArchipelago.raise(game);
    }
    empty(game);
    int money = game.wallet();
    game.travelTo(0);
    TestOutput out = new TestOutput();
    Speak s = new Speak(game, new TestInput("no", "basic", "no", "yes"), out);
    assertEquals(s.confirmPurchase(savedNumber), Action.CONFIRMPURCHASE);
    assertEquals(game.wallet(), money);
    savedNumber.add(1);
    savedNumber.add(0);
    assertEquals(s.confirmPurchase(savedNumber), Action.ONLAND);
    assertEquals(game.wallet(), money);
    savedNumber.add(1);
    savedNumber.add(0);
    assertEquals(s.confirmPurchase(savedNumber), Action.ONLAND);
    assertEquals(game.wallet(), money - game.costItem(1, 0));
    assertEquals(game.cargoSpace(), 19);
  }
  
  @Test
  void testSell() {
    Archipelago game = new Archipelago();
    ArrayList<Integer> savedNumber = new ArrayList<>();
    savedNumber.add(1);
    for (int i = 0; i < 20; i++) {
      TestArchipelago.raise(game);
    }
    game.travelTo(0);
    TestOutput out = new TestOutput();
    Speak s = new Speak(game, new TestInput("no", "basic", "cancel", "1", "max"), out);
    assertEquals(s.sell(savedNumber), Action.SELL);
    savedNumber.add(1);
    assertEquals(s.sell(savedNumber), Action.ONLAND);
    savedNumber.add(1);
    assertEquals(s.sell(savedNumber), Action.CONFIRMSELL);
    assertEquals(savedNumber.remove(0), 1);
    assertEquals(savedNumber.remove(0), 1);
    savedNumber.add(1);
    assertEquals(s.sell(savedNumber), Action.CONFIRMSELL);
    assertEquals(savedNumber.remove(0), 20);
    assertEquals(savedNumber.remove(0), 1);
  }
  
  @Test
  void testConfirmSell() {
    Archipelago game = new Archipelago();
    ArrayList<Integer> savedNumber = new ArrayList<>();
    savedNumber.add(1);
    savedNumber.add(1);
    for (int i = 0; i < 20; i++) {
      TestArchipelago.raise(game);
    }
    int money = game.wallet();
    game.travelTo(1);
    TestOutput out = new TestOutput();
    Speak s = new Speak(game, new TestInput("no", "basic", "no", "yes"), out);
    assertEquals(s.confirmSell(savedNumber), Action.CONFIRMSELL);
    assertEquals(game.wallet(), money);
    savedNumber.add(1);
    savedNumber.add(1);
    assertEquals(s.confirmSell(savedNumber), Action.ONLAND);
    assertEquals(game.wallet(), money);
    savedNumber.add(1);
    savedNumber.add(1);
    assertEquals(s.confirmSell(savedNumber), Action.ONLAND);
    assertEquals(game.wallet(), money + game.costItem(1, 1));
    assertEquals(game.cargoSpace(), 1);
  }
  
  @Test
  void testAnswer() {
    TestOutput out = new TestOutput();
    Archipelago game = new Archipelago();
    Speak s = new Speak(game, new TestInput("no", "quit"), out);
    assertEquals(s.getAnswer(a -> {
      return Action.ONLAND;
    }), Action.QUIT);
  }
  
  private void empty(Archipelago game) {
    game.travelTo(0);
    game.sellItem(20, 0);
    game.sellItem(20, 1);
    game.sellItem(20, 2);
    game.sellItem(20, 3);
  }

}
