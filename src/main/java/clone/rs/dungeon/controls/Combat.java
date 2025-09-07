package clone.rs.dungeon.controls;

import clone.rs.dungeon.character.Player;
import clone.rs.dungeon.character.Enemy;

import java.util.Scanner;

public class Combat {
  private static final Scanner scanner = new Scanner(System.in);

  public static void fight(Player player, Enemy enemy) {
    System.out.println("⚔️ You approach " + enemy.getName() + "!");
    while (player.getHealth() > 0 && enemy.getHealth() > 0) {
      System.out.printf(
              "%n"
                      + "1️⃣  Attack ⚔️%n"
                      + "2️⃣  Escape 🏃‍♂️%n"
                      + "3️⃣  Use item 🎒%n"
      );
      String input = scanner.nextLine();
      switch (input) {
        case "1" -> attack(player, enemy);
        case "2" -> {
          escape(player); return;
        }
        case "3" -> useItem(player);
      }
    }
  }

  private static void attack(Player player, Enemy enemy) {
    double damage = player.hit();
    enemy.setHealth(enemy.getHealth() - damage);
    player.addExperience((int) (damage * 10));
    System.out.printf("💥 You hit %s for %.1f damage! 🩸 Enemy HP: %.1f%n",
            enemy.getName(), damage, enemy.getHealth());

    if (enemy.getHealth() > 0) {
      double enemyDamage = enemy.hit();
      player.setHealth(player.getHealth() - enemyDamage);
      System.out.printf("🗡️ %s hits you for %.1f damage! ❤️ Your HP: %.1f%n",
              enemy.getName(), enemyDamage, player.getHealth());
    } else {
      System.out.printf("⚔️ You defeated %s! 🎉%n", enemy.getName());
      player.addItem(player.getItem());
    }
  }

  private static void escape(Player player) {
    player.setHealth(player.getHealth() - 1);
    System.out.println("🏃‍♂️ You escaped! The enemy hit you for 1 damage while fleeing.");
  }

  private static void useItem(Player player) {
    if (player.showInventory(false, true)) {
      System.out.print("🍖 Enter the name of the food you want to eat: ");
      String itemName = scanner.nextLine();
      player.useItem(itemName);
    } else {
      System.out.println("📦 Your inventory is empty.");
    }
  }
}
