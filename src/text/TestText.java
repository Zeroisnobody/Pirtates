package text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import logic.Archipelago;
import org.junit.jupiter.api.Test;
import text.Main.Input;
import text.Main.Output;

class TestText {

  @Test
  void test1() {
    TestInput in = new TestInput("A", "B");
    assertTrue(in.read().equals("A"));
  }

  @Test
  void test2() {
    TestOutput out = new TestOutput();
    out.print("A");
    check(out, 0, "A");
  }

  @Test
  void test3() {
    TestOutput out = new TestOutput();
    new Speak(new Archipelago(), new TestInput("no"), out);
    check(out, 0, "Welcome to Fort Ridley!");
    check(out, 1, "Visit the trading posts of the "
        + "different islands. Compare their prices and trade wisely to amass "
        + "great wealth!");
    check(out, 2, "Head to the shipyards at Dragon Cove "
        + "to upgrade your ship and dominate the seas!");
    check(out, 3, "Keep an eye out for talent. Hiring "
        + "new crew members is well worth the price!");
  }
  
  @Test
  void test4() {
    TestOutput out = new TestOutput();
    new Speak(new Archipelago(), new TestInput("yes", "quit"), out);
    check(out, 4, "Set Sail?");
    check(out, 5, "You are sailing at 6 knots through the clear waters.");
    check(out, 6, "Where do you want to head?");
    check(out, 7, "\tDragon Cove (0)");
    check(out, 8, "\tBouffant Bay (1)");
    check(out, 9, "\tPirate Outpost (2)");
    check(out, 10, "\tParrot Port (3)");
    check(out, 11, "\tGolden Harbor (4)");
    check(out, 12, "Thank you for playing.");
  }
  

  @Test
  void test5() {
    TestOutput out = new TestOutput();
    new Speak(new Archipelago(), new TestInput("yes", "g", "quit"), out);
    check(out, 12, "Please enter the number of the island you want to travel to.");
  }
  
  @Test
  void test6() {
    TestOutput out = new TestOutput();
    new Speak(new Archipelago(), new TestInput("yes", "0", "quit"), out);
    check(out, 12, "You have arrived at Dragon Cove");
    check(out, 13, "What do you want to do?");
    check(out, 14, "Trade (0)");
    check(out, 15, "Look Around (1)");
    check(out, 16, "Set Sail (2)");
  }
  
  @Test
  void test7() {
    TestOutput out = new TestOutput();
    new Speak(new Archipelago(), new TestInput("yes", "0", "g", "quit"), out);
    check(out, 17, "Choose the number of the action you wish to perform.");
  }

  @Test
  void test8() {
    TestOutput out = new TestOutput();
    new Speak(new Archipelago(), new TestInput("yes", "0", "0", "quit"), out);
    check(out, 17, "The prices for buying and selling on this island are :");
    check(out, 18, "\tGrain = D28\tSpices = D34");
    check(out, 19, "\tMedicine = D23\tSilk = D14");
    check(out, 20, "Do you wish to purchase (0) or sell (1)?");
  }

  @Test
  void test9() {
    TestOutput out = new TestOutput();
    new Speak(new Archipelago(), new TestInput("yes", "0", "0", "g", "quit"), out);
    check(out, 21, "Choose the number of the trade you wish to perform.");
  }
  
  @Test
  void test10() {
    TestOutput out = new TestOutput();
    new Speak(new Archipelago(), new TestInput("yes", "0", "0", "0", "quit"), out);
    check(out, 21, "Doubloons = D0\tCargo Space = 13/20");
    check(out, 22, "What do you wish to buy?");
  }

  @Test
  void test11() {
    TestOutput out = new TestOutput();
    new Speak(new Archipelago(), new TestInput("yes", "0", "0", "0", "cancel", "quit"), out);
    check(out, 23, "What do you want to do?");
  }
  
  protected static class TestInput extends Input {
    List<String> messages = new ArrayList<>();
    
    @Override
    public String read() {
      return messages.remove(0);
    }
    
    public TestInput(String... strings) {
      messages.addAll(Arrays.asList(strings));
    }    
  }
  
  protected static class TestOutput extends Output {

    ArrayList<String> messages = new ArrayList<>();
    
    @Override
    public void print(String message) {
      messages.add(message);
    }
  }
  
  protected static void check(TestOutput out, int number, String message) {
    String a = out.messages.get(number);
    String b = message;
    assertEquals(a, b);
  }

}
