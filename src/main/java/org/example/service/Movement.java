package org.example.service;

import org.example.map.DungeonGrid;
import org.example.entities.Character;

public class Movement {

    public static void moveNorth(DungeonGrid grid, Character character) {
        int newY = character.getY() - 1;
        if (!grid.getTiles(character.getX(), newY).isWall()) {
            character.setY(newY);
        } else {
            System.out.println("You can't go that way!");
        }
    }

    public static void moveSouth(DungeonGrid grid, Character character) {
        int newY = character.getY() + 1;
        if (!grid.getTiles(character.getX(), newY).isWall()) {
            character.setY(newY);
        } else {
            System.out.println("You can't go that way!");
        }
    }

    public static void moveEast(DungeonGrid grid, Character character) {
        int newX = character.getX() + 1;
        if (!grid.getTiles(newX, character.getY()).isWall()) {
            character.setX(newX);
        } else {
            System.out.println("You can't go that way!");
        }
    }

    public static void moveWest(DungeonGrid grid, Character character) {
        int newX = character.getX() - 1;
        if (!grid.getTiles(newX, character.getY()).isWall()) {
            character.setX(newX);
        } else {
            System.out.println("You can't go that way!");
        }
    }
}

