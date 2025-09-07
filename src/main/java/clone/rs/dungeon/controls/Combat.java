package clone.rs.dungeon.controls;

import clone.rs.dungeon.character.Player;
import clone.rs.dungeon.character.Enemy;

import java.util.Scanner;

public class Combat {
  private static final Scanner scanner = new Scanner(System.in);

  public static void fight(Player player, Enemy enemy) {
    System.out.println("You went to " + enemy.getName());
    while (player.getHealth() > 0 && enemy.getHealth() > 0) {
      System.out.printf("%n1. Attack %n2. Escape%n3. Use item%n");
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
    System.out.println("You hit " + enemy.getName() +
            " for " + damage + " damage. Enemy HP: " + enemy.getHealth());

    if (enemy.getHealth() > 0) {
      double enemyDamage = enemy.hit();
      player.setHealth(player.getHealth() - enemyDamage);
      System.out.println(enemy.getName() + " hits you for " +
              enemyDamage + " damage. Your HP: " + player.getHealth());
    } else {
      System.out.println("You killed " + enemy.getName());
      player.addItem(player.getItem());
    }
  }

  private static void escape(Player player) {
    player.setHealth(player.getHealth() - 1);
    System.out.println("You ran away! While you were running, the enemy hit you for 1 damage.");
  }

  private static void useItem(Player player) {
    if (player.showInventory(false, true)) {
      System.out.println("Enter food name: ");
      String itemName = scanner.nextLine();
      player.useItem(itemName);
    } else {
      System.out.println("Inventory is empty.");
    }
  }
}
