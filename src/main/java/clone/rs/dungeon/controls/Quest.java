package clone.rs.dungeon.controls;

import clone.rs.dungeon.character.Enemy;
import clone.rs.dungeon.character.Player;

import java.util.Scanner;

import static clone.rs.dungeon.controls.Controls.fight;

public class Quest {

  private static final Scanner scanner = new Scanner(System.in);

  public static void start(Player player) {
    System.out.println("=== Dragon Slayer Quest ===");
    System.out.println("You enter the town of Lumbridge. The village elder approaches you...");

    pause(2000);
    System.out.println("\nElder: \"Greetings, brave adventurer. I have a task for you.\"");
    pause(2000);
    System.out.println("Elder: \"A fearsome dragon has appeared near the mountains. We need someone to slay it.\"");
    pause(2000);

    int bonesCount = player.checkItem("Bones");
    if (bonesCount < 50) {
      System.out.println("\nElder: \"You are not prepared. You need 50 bones to complete your preparation.\"");
      System.out.println("You currently have: " + bonesCount + " bones.");
      System.out.println("Collect more bones and come back!");
      return;
    }

    System.out.println("\nElder: \"Ah, you have enough bones! Well done.\"");
    System.out.println("Elder: \"Now, travel to the Dragon's Lair and prepare for battle.\"");
    pause(2000);

    System.out.println("\nYou journey through the dark forest and arrive at the dragon's cave...");
    pause(3000);

    System.out.println("\nA massive shadow looms ahead. You hear the dragon's roar echoing in the cave!");
    pause(3000);

    System.out.println("\n[You are ready to fight the dragon!]");
    System.out.println("Press Enter to continue to the battle...");
    scanner.nextLine();

    fight(player, new Enemy("Green dragon", 75, 79, 10));
  }

  private static void pause(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}