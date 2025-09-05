package org.example.service;

import org.example.entities.Player;
import org.example.map.Dungeon;

public class MovementLogic {

    public void movePlayer(Player player, String direction, Dungeon dungeon) {
        int newRow = player.getRow();
        int newCol = player.getCol();

        switch(direction.toLowerCase()) {
            case "up":    newRow--; break;
            case "down":  newRow++; break;
            case "left":  newCol--; break;
            case "right": newCol++; break;
        }

        // kolla om tile är walkable och inom dungeon
        if (newRow >= 0 && newRow < dungeon.getRows() &&
                newCol >= 0 && newCol < dungeon.getCols() &&
                dungeon.getTile(newRow, newCol).isWalkable()) {

            player.moveTo(newRow, newCol);
            System.out.println("You moved " + direction);
        } else {
            System.out.println("Can't move there!");
        }
    }
}
