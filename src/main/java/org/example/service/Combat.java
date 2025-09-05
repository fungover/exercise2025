package org.example.service;

import org.example.entities.*;
import org.example.entities.items.HealthPotion;
import org.example.entities.items.Weapon;
import org.example.utils.RandomGenerator;

public class Combat {

    public void startFight(Player p, Weapon w, HealthPotion hP, String userInput) {
        Enemy e = randomEnemy();
        boolean isFighting = true;
        System.out.println("It's a " + e.getName() + "!");
        while (isFighting) {
            System.out.println("What would you like to do?");
            System.out.println("1. Fight");
            System.out.println("2. Use an item");
            System.out.println("3. Run away");
            System.out.print("Choose 1, 2 or 3: ");

            switch (userInput) {
                case "1":
                    attackEnemy(w, e);
                    attackByEnemy(p, e);
                    if (p.getHealth() <= 0) {
                        System.out.println("You died!");
                        isFighting = false;
                    } else if (e.getHealth() <= 0) {
                        System.out.println("You won!");
                        isFighting = false;
                    }
                    break;
                case "2":
                    if (hP.getQuantity() == 0) {
                        System.out.println("You have no potions!");
                    } else {
                        System.out.println("You use a health potion!");
                        p.setHealth(hP.restoreHealth());
                    }
                    break;
                case "3":
                    System.out.println("You run away!");
                    isFighting = false;
                default:
                    System.out.println("That is not an option!");
            }
        }
    }

    private void attackEnemy(Weapon w, Enemy e) {
        int rand = new RandomGenerator().generateNumber(1, 10);
        if (rand < 7) {
            System.out.println("You swing your sword!\nDealing " +
                    w.getDamage() + " damage!");
            damageGiven(e, w.getDamage());
        } else {
            System.out.println("You missed!");
        }
    }

    private void attackByEnemy(Player p, Enemy e) {
        int rand = new RandomGenerator().generateNumber(1, 10);
        if (rand < 7) {
            System.out.println("The " + e.getName() + " strikes back!"
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
