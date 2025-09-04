package org.example.service;

import org.example.entities.Player;
import org.example.map.Room;
import org.example.map.Tile;

public class Movement {

    public void renderPlayerPos(Room room, int x, int y) {
        // Here grid[i][j] == grid[y][x] from Room class
        for (int i = 0; i < room.getRows(); i++) {
            for (int j = 0; j < room.getColumns(); j++) {
                if (room.getGrid(i, j) == Tile.PLAYER.getTile()) {
                    room.setGrid(i, j, Tile.FLOOR.getTile());
                }
            }
        }
        room.setGrid(y, x, Tile.PLAYER.getTile());
    }

    public void moveInput(Room room, Player player, String dir) {
        Action action = new Action();
        dir = dir.toLowerCase();

        int x = player.getX();
        int y = player.getY();

        switch (dir) {
            case "move north", "n":
                if (action.walkable(room, y-1, x))
                    player.setPosition(x, y-1);
                action.fightEnemy(room, y-1, x);
                action.pickItem(room, y-1, x);
                break;
            case "move south", "s":
                if (action.walkable(room, y+1, x))
                    player.setPosition(x, y + 1);
                action.fightEnemy(room, y+1, x);
                action.pickItem(room, y+1, x);
                break;
            case "move east", "e":
                if (action.walkable(room, y, x+1))
                    player.setPosition(x + 1, y);
                action.fightEnemy(room, y, x+1);
                action.pickItem(room, y, x+1);
                break;
            case "move west", "w":
                if (action.walkable(room, y, x-1))
                    player.setPosition(x - 1, y);
                action.fightEnemy(room, y, x-1);
                action.pickItem(room, y, x-1);
                break;
            default:
                System.out.println("Invalid input, only type what is displayed.");
                break;
        }
    }
}
