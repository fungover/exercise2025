package org.example.service;

import org.example.entities.characters.Player;
import org.example.map.DungeonGrid;
import org.example.map.Tile;

public class MovementService {
    public boolean movePlayer(Player player, DungeonGrid grid, int dx, int dy) {
        int oldX = player.getX();
        int oldY = player.getY();
        int newX = oldX + dx;
        int newY = oldY + dy;

        int width = grid.getWidth();
        int height = grid.getHeight();

        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
            System.out.println("You cannot move there");
            return false;
        }

        Tile.TileType nextTileType = grid.getTiles()[newX][newY].getType();

        if (nextTileType == Tile.TileType.FLOOR) {
            grid.getTiles()[oldX][oldY].setHasPlayer(false);

            player.setX(newX);
            player.setY(newY);
            grid.getTiles()[newX][newY].setHasPlayer(true);

            System.out.println("> You moved.");
            return false;
        } else if (nextTileType == Tile.TileType.DOOR) {
            System.out.println("You left the dungeon");
            return true;
        } else if (nextTileType == Tile.TileType.EXIT) {
            System.out.println("You found the exit!");
            return true;
        } else {
            System.out.println("You cannot move there, it's a wall");
            return false;
        }
    }

    public void availableMoves(Player player, DungeonGrid grid) {
        int oldX = player.getX();
        int oldY = player.getY();

        System.out.println("Where would you like to move?");

        // North
        if (oldY - 1 >= 0) {
            Tile.TileType northTile = grid.getTiles()[oldX][oldY - 1].getType();
            if (northTile == Tile.TileType.DOOR) {
                System.out.println("N (Door) ");
            } else if (northTile == Tile.TileType.EXIT) {
                System.out.println("N (Exit) ");
            } else if (northTile == Tile.TileType.FLOOR) {
                System.out.println("N ");
            }
        }

        // East
        if (oldX + 1 < grid.getWidth()) {
            Tile.TileType eastTile = grid.getTiles()[oldX + 1][oldY].getType();
            if (eastTile == Tile.TileType.DOOR) {
                System.out.println("E (Door) ");
            } else if (eastTile == Tile.TileType.EXIT) {
                System.out.println("E (Exit) ");
            } else if (eastTile == Tile.TileType.FLOOR) {
                System.out.println("E ");
            }
        }

        // South
        if (oldY + 1 < grid.getHeight()) {
            Tile.TileType southTile = grid.getTiles()[oldX][oldY + 1].getType();
            if (southTile == Tile.TileType.DOOR) {
                System.out.println("S (Door) ");
            } else if (southTile == Tile.TileType.EXIT) {
                System.out.println("S (Exit) ");
            } else if (southTile == Tile.TileType.FLOOR) {
                System.out.println("S ");
            }
        }

        // West
        if (oldX - 1 >= 0) {
            Tile.TileType westTile = grid.getTiles()[oldX - 1][oldY].getType();
            if (westTile == Tile.TileType.DOOR) {
                System.out.println("W (Door) ");
            } else if (westTile == Tile.TileType.EXIT) {
                System.out.println("W (Exit) ");
            } else if (westTile == Tile.TileType.FLOOR) {
                System.out.println("W ");
            }
        }

        System.out.println("Press I to open inventory");
    }
}
