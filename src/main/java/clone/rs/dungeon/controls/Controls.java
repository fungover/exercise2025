package clone.rs.dungeon.controls;

import clone.rs.dungeon.character.Character;
import clone.rs.dungeon.character.Enemy;
import clone.rs.dungeon.character.Player;

import java.io.IOException;
import java.util.Scanner;

public class Controls {
  private static final Scanner scanner = new Scanner(System.in);

  public static void menu() throws IOException {
    Player player = null;

    while (player == null) {
      System.out.printf("1. Create new character%n2. Load character%n3. Exit%n");
      String input = scanner.nextLine();

      switch (input) {
        case "1" -> {
          player = createCharacter();
          System.out.println("Your character " + player.getName() + " has been created.");
        }
        case "2" -> {
          player = SaveLoad.loadPlayer();
          if (player != null) {
            System.out.println("Loaded character: " + player.getName());
          } else {
            System.out.println("No saved character found.");
          }
        }
        case "3" -> {
          System.out.println("Goodbye!");
          return;
        }
        default -> System.out.println("Invalid choice!");
      }
    }

    while (true) {
      System.out.printf("%n1. Attack goblin%n2. Escape%n9. Exit%n");
      String input2 = scanner.nextLine();

      switch (input2) {
        case "1" -> {
          Enemy goblin = new Enemy("Goblin", 5, 3);
          fight(player, goblin);
        }
        case "2" -> System.out.println("Escaped!");
        case "9" -> {
          System.out.println("Game over.");
          return;
        }
        default -> System.out.println("Invalid input.");
      }
    }
  }

  private static void fight(Player player, Enemy goblin) {
    System.out.println("Fight");
  }

  private static Player createCharacter() throws IOException {
    System.out.println("Enter character name:");
    String name = System.console().readLine();
    Player player = new Player( name, 10, 3);
    SaveLoad.savePlayer(player);
    return player;
  }
}


