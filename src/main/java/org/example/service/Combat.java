package org.example.service;

import org.example.entities.*;
import org.example.entities.items.HealthPotion;
import org.example.entities.items.Inventory;
import org.example.entities.items.Weapon;
import org.example.utils.RandomGenerator;

import java.util.Scanner;

public class Combat {
    private final RandomGenerator rand = new RandomGenerator();

    public void startFight(Player p, Inventory inventory, Enemy e, Scanner scan) {
        Weapon w = inventory.getItem(Weapon.class);

        System.out.println("It's a " + e.getName() + "!");
        while (true) {
            p.displayHealth();
            System.out.println("What would you like to do?");
            System.out.println("1. Fight");
            System.out.println("2. Use an item");
            System.out.println("3. Run away");
            System.out.print("Choose 1, 2 or 3: ");
            String userInput = scan.nextLine();

            switch (userInput) {
                case "1":
                    System.out.println(); // Line Break
                    attackEnemy(w, e);
                    if (e.getHealth() <= 0) {
                        System.out.println("You won!");
                        return;
                    }
                    attackByEnemy(p, e);
                    if (p.getHealth() <= 0) {
                        System.out.println("You died!");
                        return;
                    }
                    break;
                case "2":
                    System.out.println();
                    HealthPotion hp = inventory.getItem(HealthPotion.class);
                    if (hp == null) {
                        System.out.println("You have no potions!");
                    } else {
                        System.out.println("You use a health potion!");
                        p.increaseHealth(hp.restoreHealth());
                        inventory.removeItem(hp);
                    }
                    break;
                case "3":
                    System.out.println();
                    if (tryRunAway(p, e)) {
                        return;
                    }
                    break;
                default:
                    System.out.println("Only write a number between 1 and 3.");
            }
        }
    }

    private boolean tryRunAway(Player p, Enemy e) {
        int randInt = rand.generateNumber(1, 10);
        if (randInt < 5) {
            System.out.println("You run away!");
            return true;
        } else {
            System.out.println("You can't get away!");
            attackByEnemy(p, e);
        }
        return false;
    }

    private void attackEnemy(Weapon w, Enemy e) {
        int randInt = rand.generateNumber(1, 10);
        int damage = w.getDamage();

        if (randInt < 7) {
            System.out.println("You swing your sword!\nDealing " +
                    damage + " damage!");
            damageDealt(e, damage);
        } else {
            System.out.println("You missed!");
        }
    }

    private void attackByEnemy(Player p, Enemy e) {
        int randInt = rand.generateNumber(1, 10);
        if (randInt <= 7) {
            System.out.println("The " + e.getName() + " strikes at you!"
                    + "\nThey dealt " + e.getDamage() + " damage!");
            damageTaken(p, e);
        } else {
            System.out.println(e.getName() + " missed!");
        }
    }

    public void damageTaken(Player p, Enemy e) {
        int damageReceived = e.getDamage();
        p.decreaseHealth(damageReceived);
    }

    public void damageDealt(Enemy e, int damage) {
        e.decreaseHealth(damage);
    }
}
