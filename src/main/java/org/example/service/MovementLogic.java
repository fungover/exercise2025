package org.example.service;

import org.example.entities.Player;
import org.example.entities.enemies.Enemy;
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

        // kolla om tile är walkable och inom dungeon
        if (newRow >= 0 && newRow < dungeon.getRows() &&
                newCol >= 0 && newCol < dungeon.getCols() &&
                dungeon.getTile(newRow, newCol).isWalkable()) {

            player.moveTo(newRow, newCol);
            Tile newTile = dungeon.getTile(newRow, newCol);
            System.out.print("You moved to a new tile.");
            if (newTile.getEnemy() != null) {
                System.out.println(" There is a  " + newTile.getEnemy().getType() + " here!");
            } else {
                System.out.println();
            }

        } else {
            System.out.println("Can't move there!");
        }
    }
}
