package org.example.service;

import org.example.entities.Player;
import org.example.map.Dungeon;
import org.example.utils.InputValidator;

public class MovementService {
    private final Dungeon dungeon;
    private final InputValidator validator;

    public MovementService(Dungeon dungeon, InputValidator validator) {
        if (dungeon == null) {
            throw new IllegalArgumentException("Dungeon cannot be null");
        }
        if (validator == null) {
            throw new IllegalArgumentException("Input validator cannot be null");
        }
        this.dungeon = dungeon;
        this.validator = validator;
        System.out.println("MovementService initialized for dungeon with dimensions " + dungeon.getWidth() + "x" + dungeon.getHeight());
    }

    public void movePlayer(int dx, int dy, Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        System.out.println("Attempting to move player from " + player.getX() + "," + player.getY() + " to " + (player.getX() + dx) + "," + (player.getY() + dy));
        if (validator.isValidMove(dx, dy, player)) {
            int newX = player.getX() + dx;
            int newY = player.getY() + dy;
            player.moveTo(newX, newY);
            System.out.println("Player moved to " + newX + "," + newY);
        } else {
            throw new IllegalArgumentException("Invalid move");
        }
    }

    public void movePlayerTo(int x, int y, Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        System.out.println("Attempting to move player from " + player.getX() + "," + player.getY() + " to " + x + "," + y);
        if (validator.isValidCoordinate(x, y) && dungeon.getTile(x, y).isPassable()) {
            player.moveTo(x, y);
            System.out.println("Player moved to " + x + "," + y);
        } else {
            throw new IllegalArgumentException("Invalid move: out of bounds or not passable tile");
        }
    }
}
