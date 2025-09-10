package org.example.service;

import org.example.entities.Player;
import org.example.entities.enemies.Enemy;
import org.example.entities.items.Item;
import org.example.entities.items.Weapon;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Combat {

    private Scanner scanner = new Scanner(System.in);

    public void fight(Player player, Enemy enemy) {

        List<Item> weapons = player.getInventory().stream()
                .filter(item -> item.getType().equals("weapon"))
                .collect(Collectors.toList());

        if (weapons.isEmpty()) {
            System.out.println("You have no weapon! Go and find one first.");
            return;
        }

        System.out.println("You have the following weapons:");
        for (int i = 0; i < weapons.size(); i++) {
            Item w = weapons.get(i);
            System.out.println((i + 1) + ") " + w.getName() + " (" + w.getEffect() + " dmg)");
        }

        int choice = -1;
        while (choice < 1 || choice > weapons.size()) {
            System.out.print("Which weapon do you want to use? ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        Item weapon = weapons.get(choice - 1);

        Weapon weaponItem = (Weapon) weapon;
        int playerDamage = weaponItem.getDamage();
        enemy.takeDamage(playerDamage);
        System.out.println("You attack the " + enemy.getType() + " with " + weapon.getName() + " for " + playerDamage + " damage.");

        if (enemy.isAlive()) {
            int enemyDamage = enemy.getDamage();
            player.setHealth(player.getHealth() - enemyDamage);
            System.out.println(enemy.getType() + " attacks back for " + enemyDamage + " damage.");
            System.out.println("Your health is now " + player.getHealth() + ".");
            System.out.println(enemy.getType() + " health is now " + enemy.getHealth() + ".");
        } else {
            System.out.println("You have defeated the " + enemy.getType() + "!");
        }
    }
}
