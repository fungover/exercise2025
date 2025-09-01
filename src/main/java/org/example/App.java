package org.example;

import org.example.entities.Enemy;
import org.example.entities.Item;
import org.example.entities.Player;
import org.example.game.Combat;

public class App {
    public static void main(String[] args) {
        //Create player
        Player player = new Player("Hero");

        //Create items and give to player
        Item sword = new Item("Sword", "A shiny sword", Item.ItemType.WEAPON, 5);
        Item potion = new Item("Healing potion", "Drink to get +30 hp", Item.ItemType.CONSUMABLE, 30);
        player.addItem(sword);
        player.addItem(potion);

        //Show player inventory and stats
        player.showInventory();
        player.showStats();

        //Create enemy
        Enemy goblin = new Enemy("Goblin", "Nasty goblin", 225, 50);

        //Start combat
        Combat combat = new Combat();
        combat.startCombat(player, goblin);

        //Show result after combat
        System.out.println("\n=== AFTER COMBAT ===");
        player.showStats();
    }

}
