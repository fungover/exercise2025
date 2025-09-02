package org.example;

import org.example.entities.Enemy;
import org.example.entities.Healing;
import org.example.entities.Player;
import org.example.entities.Weapon;
import org.example.game.Combat;

public class App {
    public static void main(String[] args) {
        //Create player
        Player player = new Player("Hero");

        //Create items and give to player
        Weapon sword = new Weapon("Sword", "A shiny sharp sword", "Weapon", 1, 20);
        Weapon sword2 = new Weapon("Sword", "A shiny sharp sword", "Weapon", 1, 20);
        Healing smallPotion = new Healing("Potion", "Heals 10 hp", "Healing", 4, 10);
        Weapon sword3 = new Weapon("Sword", "A shiny sharp sword", "Weapon", 1, 20);
        Healing smallPotion1 = new Healing("Potion", "Heals 10 hp", "Healing", 4, 10);
        player.addItem(sword);
        player.addItem(sword2);
        player.addItem(smallPotion);
        player.addItem(smallPotion1);
        player.addItem(sword3);

        //Show player inventory and stats
        player.showInventory();
        player.showStats();
        sword.displayInfo();
        sword2.displayInfo();
        smallPotion.displayInfo();



        //Create enemy
        Enemy goblin = new Enemy("Goblin", "Nasty goblin", 225, 5);

        //Start combat
        Combat combat = new Combat();
        combat.startCombat(player, goblin);

        //Show result after combat
        System.out.println("\n=== AFTER COMBAT ===");
        player.showStats();
        player.showInventory();
    }

}
