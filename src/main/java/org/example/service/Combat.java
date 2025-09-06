package org.example.service;

import org.example.entities.*;
import org.example.entities.items.HealthPotion;
import org.example.entities.items.Inventory;
import org.example.entities.items.Weapon;
import org.example.utils.RandomGenerator;

import java.util.Scanner;

public class Combat {

    public void startFight(Player p, Inventory inventory, Scanner scan) {
        Enemy e = randomEnemy();
        Weapon w = inventory.getWeapon();
        HealthPotion hp = inventory.getHealthPotion();

        boolean isFighting = true;
        System.out.println("It's a " + e.getName() + "!");
        while (isFighting) {
            System.out.println("What would you like to do?");
            System.out.println("1. Fight");
            System.out.println("2. Use an item");
            System.out.println("3. Run away");
            System.out.print("Choose 1, 2 or 3: ");
            String userInput = scan.nextLine();

            switch (userInput) {
                case "1":
                    attackEnemy(w, e);
                    if (e.getHealth() <= 0) {
                        System.out.println("You won!");
                        isFighting = false;
                    }
                    attackByEnemy(p, e);
                    if (p.getHealth() <= 0) {
                        System.out.println("You died!");
                        isFighting = false;
                    }
                    break;
                case "2":
                    if (hp == null) {
                        System.out.println("You have no potions!");
                    } else {
                        System.out.println("You use a health potion!");
                        p.setHealth(hp.restoreHealth());
                        inventory.removeItem(hp);
                    }
                    break;
                case "3":
                    isFighting = tryRunAway(p, e);
                default:
                    System.out.println("That is not an option!");
            }
        }
    }

    private boolean tryRunAway(Player p, Enemy e) {
        int rand = new RandomGenerator().generateNumber(1, 10);
        if (rand < 5) {
            System.out.println("You run away!");
            return false;
        } else {
            System.out.println("You can't get away!");
            attackByEnemy(p, e);
        }
        return true;
    }

    private void attackEnemy(Weapon w, Enemy e) {
        int rand = new RandomGenerator().generateNumber(1, 10);
        int damage;

        // If player has no weapon in hand.
        if (w == null) {
            damage = rand;
        } else {
            damage = w.getDamage();
        }

        if (rand < 7) {
            System.out.println("You swing your sword!\nDealing " +
                    damage + " damage!");
            damageGiven(e, damage);
        } else {
            System.out.println("You missed!");
        }
    }

    private void attackByEnemy(Player p, Enemy e) {
        int rand = new RandomGenerator().generateNumber(1, 10);
        if (rand < 7) {
            System.out.println("The " + e.getName() + " strikes at you!"
                    + "\nThey dealt " + e.getDamage() + " damage!");
            damageTaken(p, e);
        } else {
            System.out.println(e.getName() + " missed!");
        }
    }

    private Enemy randomEnemy() {
        int rand = new RandomGenerator().generateNumber(1, 10);
        if  (rand < 4) {
            return new Goblin();
        } else if (rand < 7) {
            return new Troll();
        } else {
            return new Dragon();
        }
    }

    private void damageTaken(Player player, Enemy enemy) {
        int damageReceived = enemy.getDamage();
        player.setHealth(damageReceived);
    }

    private void damageGiven(Enemy enemy, int damage) {
        int damageDealt = enemy.getHealth() - damage;
        enemy.setHealth(damageDealt);
    }
}
