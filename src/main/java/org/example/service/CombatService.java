package org.example.service;

import org.example.entities.*;
import org.example.map.FarmageddonMap;
import org.example.map.Tile;

public class CombatService {
    private static final int BASE_DAMAGE = 5;

    public void attack(Player player, FarmageddonMap map) {
        Tile tile = map.getTile(player.getX(), player.getY());
        Hostile hostile = tile.getEnemy();

        if (tile.getType() == Tile.Type.ENEMY && hostile != null) {
            System.out.println("Combat begins!");

            if (hostile instanceof Enemy enemy) {
                System.out.println("A " + enemy.getName() + " appears! (ATK: " + enemy.getDamage() +
                        ", HP: " + enemy.getHealth() + "/" + enemy.getMaxHealth() + ")");
            }

            // Enemy attacks first
            System.out.println("\n--- Enemy's turn ---");
            hostile.attack(player);
            System.out.println("Your current health: " + player.getHealth() + " HP");

            if (!player.isAlive()) {
                System.out.println("You have been defeated!");
                return;
            }

            // Player attacks next
            System.out.println("\n--- Your turn ---");
            int playerDamage = calculatePlayerDamage(player);

            if (hostile instanceof Enemy enemy) {
                enemy.takeDamage(playerDamage);

                if (!enemy.isAlive()) {
                    System.out.println("You defeated the " + enemy.getName() + "!");
                    enemy.defeat(tile);
                } else {
                    System.out.println("You dealt " + playerDamage + " damage!");
                    System.out.println(enemy.getName() + " has " + enemy.getHealth() + "/" +
                            enemy.getMaxHealth() + " HP left.");
                }
            }

        } else {
            System.out.println("There's nothing to attack here.");
        }
    }

    private int calculatePlayerDamage(Player player) {
        int totalDamage = BASE_DAMAGE;
        Item bestWeapon = null;

        // Find weapon with highest damage in inventory and use it
        for (Item item : player.getInventory()) {
            if (item instanceof Weapon weapon) {
                if (bestWeapon == null || weapon.getDamage() > ((Weapon) bestWeapon).getDamage()) {
                    bestWeapon = item;
                }
            }
        }

        if (bestWeapon != null) {
            Weapon weapon = (Weapon) bestWeapon;
            totalDamage = weapon.getDamage();
            System.out.println("You attack with your " + weapon.getName() + "!");
        } else {
            System.out.println("You attack with your bare hands!");
        }

        return totalDamage;
    }
}