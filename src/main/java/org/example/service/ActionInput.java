package org.example.service;

import org.example.map.Room;
import org.example.map.Tile;

public class ActionInput {
    public void fightEnemy(Room room, int y, int x) {
        if (room.getGrid(y, x) == Tile.ENEMY.getTile()) {
            System.out.println("There's an enemy here!");
        }
    }

    public boolean walkable(Room room, int y, int x) {
        return room.getGrid(y, x) == Tile.FLOOR.getTile();
    }

    public void pickItem(Room room, int y, int x) {
        if (room.getGrid(y, x) == Tile.ITEM.getTile()) {
            System.out.println("There's an item here!");
        }
    }

    public void useItem() {}
}
