package org.example.service;

import org.example.entities.Player;
import org.example.entities.enemies.Enemy;
import org.example.entities.items.Item;
import org.example.map.Dungeon;
import org.example.map.Tile;

public class MovementLogic {

    public void movePlayer(Player player, String direction, Dungeon dungeon) {
        int newRow = player.getRow();
        int newCol = player.getCol();

        switch (direction.toLowerCase()) {
            case "up":
                newRow--;
                break;
            case "down":
                newRow++;
                break;
            case "left":
                newCol--;
                break;
            case "right":
                newCol++;
                break;
        }


        if (newRow >= 0 && newRow < dungeon.getRows() &&
                newCol >= 0 && newCol < dungeon.getCols() &&
                dungeon.getTile(newRow, newCol).isWalkable()) {

            player.moveTo(newRow, newCol);
            Tile newTile = dungeon.getTile(newRow, newCol);
            System.out.println("You moved to a new tile.");

        } else {
            System.out.println("Can't move there!");
        }
    }
}
