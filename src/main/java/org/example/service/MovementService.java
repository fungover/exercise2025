package org.example.service;

import org.example.entities.Item;
import org.example.entities.Player;
import org.example.map.FarmageddonMap;
import org.example.map.Tile;

public class MovementService {
    public void move(Player player, FarmageddonMap map, int dx, int dy) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        if (newX < 0 || newX >= map.getWidth() || newY < 0 || newY >= map.getHeight()) {
            System.out.println("You can't move outside the farm!");
            return;
        }

        Tile tile = map.getTile(newX, newY);
        if (tile.getType() == Tile.Type.WALL) {
            System.out.println("Ooops! You bump into a wall!");
            return;
        }

        player.move(dx, dy);
        System.out.println("You moved to (" + newX + ", " + newY + ")");

        if (tile.getType() == Tile.Type.ENEMY) {
            System.out.println("Oh no! There's an enemy here!");
            CombatService combatService = new CombatService();
            combatService.attack(player, map);
        } else if (tile.getType() == Tile.Type.ITEM) {
            Item item = tile.getItem();
            if (item != null) {
                player.addItem(item);
                System.out.println("You picked up: " + item.getName() + "!");

                tile.removeItem();
                System.out.println("The item has been added to your inventory.");
            } else {
                System.out.println("You found an item, but couldn't pick it up.");
            }
        } else {
            System.out.println("Nothing special here.");
        }
    }
}
