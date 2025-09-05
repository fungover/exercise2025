package org.example.service;

import org.example.entities.Player;
import org.example.entities.Tile;

public class MovementService {

    public static void movePlayer(String command, Player player, Tile[][] tile) {
        int newX = player.getX();
        int newY = player.getY();

        switch (command.toLowerCase()) {
            case "go north" -> newX--;
            case "go south" -> newX++;
            case "go east" -> newY--;
            case "go west" -> newY++;
            case "look" -> {
                lookAround(player, tile);
                return;
            }
            default -> {
                System.out.println("Invalid command. Acceptable commands: go north, go south, go east, go west.");
                return;
            }
        }

        //condition to check map boundaries
        if(newX < 0 || newY < 0 || newX >= tile.length || newY >= tile[0].length) {
            System.out.println("You can't move outside the map");
            return;
        }

        //check if tile is walkable
        Tile target =  tile[newX][newY];
        if(!target.isWalkable()) {
            System.out.println("You bump into a wall");
            return;
        }

        //move player
        player.setX(newX);
        player.setY(newY);
        target.setVisited(true);
        System.out.println("You have moved one tile");
    }

    private static void lookAround(Player player, Tile[][] tile) {
        int x = player.getX();
        int y = player.getY();

        tile[x][y].setVisited(true);
        if(x - 1 >= 0) tile[x - 1][y].setVisited(true); //make north visible
        if(x + 1 < tile.length) tile[x + 1][y].setVisited(true); //make south visible
        if(y - 1 >= 0) tile[x][y - 1].setVisited(true); //make west visible
        if(y + 1 < tile[0].length) tile[x][y + 1].setVisited(true); //make east visible

        System.out.println("You look around and observe the surrounding tiles");
    }

}
