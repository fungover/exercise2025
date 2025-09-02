package org.example.service;

import org.example.entities.characters.Player;
import org.example.map.DungeonGrid;
import org.example.map.Tile;

import java.io.BufferedReader;

public class MovementService {
    public boolean movePlayer(Player player, DungeonGrid grid, int dx, int dy) {
        int oldX = player.getX();
        int oldY = player.getY();

        int newX = oldX + dx;
        int newY = oldY + dy;

        if (grid.getTiles()[newX][newY].getType() == Tile.TileType.FLOOR) {
            grid.getTiles()[oldX][oldY].setHasPlayer(false);

            player.setX(newX);
            player.setY(newY);
            grid.getTiles()[newX][newY].setHasPlayer(true);

            System.out.println("> You moved.");
            return false;
        } else if (grid.getTiles()[newX][newY].getType() == Tile.TileType.DOOR){
            System.out.println("You left the dungeon");
            return true;
        } else {
            System.out.println("You cannot move there, it's a wall");
            return false;
        }
    }

    public void availableMoves(Player player, DungeonGrid grid) {
        int oldX = player.getX();
        int oldY = player.getY();

        String north;

        System.out.println("Where would you like to move?");

        //North
        if (grid.getTiles()[oldX][oldY - 1].getType() == Tile.TileType.DOOR) {
            System.out.println("N (Door) ");
        } else if (grid.getTiles()[oldX][oldY - 1].getType() == Tile.TileType.FLOOR) {
            System.out.println("N ");
        }

        //East
        if (grid.getTiles()[oldX + 1][oldY].getType() == Tile.TileType.DOOR) {
            System.out.println("E (Door) ");
        } else if (grid.getTiles()[oldX + 1][oldY].getType() == Tile.TileType.FLOOR) {
            System.out.println("E ");
        }

        //South
        if (grid.getTiles()[oldX][oldY + 1].getType() == Tile.TileType.DOOR) {
            System.out.println("S (Door) ");
        } else if (grid.getTiles()[oldX][oldY + 1].getType() == Tile.TileType.FLOOR) {
            System.out.println("S ");
        }

        //West
        if (grid.getTiles()[oldX - 1][oldY].getType() == Tile.TileType.DOOR) {
            System.out.println("W (Door) ");
        } else if (grid.getTiles()[oldX - 1][oldY].getType() == Tile.TileType.FLOOR) {
            System.out.println("W ");
        }
    }
}
