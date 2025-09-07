package clone.rs.dungeon.controls;

import clone.rs.dungeon.character.Character;
import clone.rs.dungeon.character.Enemy;
import clone.rs.dungeon.character.Player;

public class Controls {
  public static void menu() {
    Player player = null;
    while (player == null) {
      System.out.printf("1. Create character%n2. Exit%n");
      String input = System.console().readLine();
      switch (input) {
        case "1" -> {
            player = createCharacter();
        }
        case "2" -> {return;}
      }

      while(true) {
        System.out.printf("1. Attack goblin %n2. Escape%n");
        String input2 = System.console().readLine();

        switch (input2) {
          case "1" -> {
            Enemy goblin = new Enemy("Goblin", 5, 3);
           fight(player, goblin);
          }
          case "2" -> {
            System.out.println("Escaped");
          }
          case "9" -> {
            System.out.println("Game over.");
            return;
          }
          default -> {
            System.out.println("Invalid input.");
          }
        }
      }

    }
  }

  private static void fight(Player player, Enemy goblin) {
    System.out.println("Fight");
  }

  private static Player createCharacter() {
    System.out.println("Enter character name:");
    String name = System.console().readLine();
    return new Player(name, 10, 3);
  }
}
