package org.example.service;

import org.example.entities.Player;
import org.example.map.Room;
import org.example.map.Tile;

public class MovementInput {

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

    public void moveInput(String input, Player player, Room room) {
        ActionInput actionInput = new ActionInput();
        int x = player.getX();
        int y = player.getY();

        switch (input) {
            case "North", "N":
                if (actionInput.walkable(room, y-1, x))
                    player.setPosition(x, y-1);
                actionInput.fightEnemy(room, y-1, x);
                actionInput.pickItem(room, y-1, x);
                break;
            case "South", "S":
                if (actionInput.walkable(room, y+1, x))
                    player.setPosition(x, y + 1);
                actionInput.fightEnemy(room, y+1, x);
                actionInput.pickItem(room, y+1, x);
                break;
            case "East", "E":
                if (actionInput.walkable(room, y, x+1))
                    player.setPosition(x + 1, y);
                actionInput.fightEnemy(room, y, x+1);
                actionInput.pickItem(room, y, x+1);
                break;
            case "West", "W":
                if (actionInput.walkable(room, y, x-1))
                    player.setPosition(x - 1, y);
                actionInput.fightEnemy(room, y, x-1);
                actionInput.pickItem(room, y, x-1);
                break;
            default:
                System.out.println("Invalid input, only type what is displayed.");
                break;
        }
    }
}
