package org.example.service;

import org.example.map.DungeonGrid;
import org.example.entities.Character;

public class Movement {

    public static void move(DungeonGrid grid, Character character, int dx, int dy) {
        int newX = character.getX() + dx; // delta - x
        int newY = character.getY() + dy; // delta - y
        if (newX >= 0 && newX < grid.getWidth() && newY >= 0 && newY < grid.getHeight()) {
            if (!grid.getTiles(newX, newY).isWall()) {
                character.setX(newX);
                character.setY(newY);
            } else {
                System.out.println("You can't go that way!");
            }
        } else {
            System.out.println("You can't go outside the map!");
        }
    }
}

