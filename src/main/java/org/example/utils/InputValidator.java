package org.example.utils;

import org.example.entities.Player;
import org.example.entities.Tile;
import org.example.map.Dungeon;

public class InputValidator {
    private final Dungeon dungeon;

    public InputValidator(Dungeon dungeon) {
        if (dungeon == null) {
            throw new IllegalArgumentException("Dungeon cannot be null");
        }
        this.dungeon = dungeon;
        System.out.println("InputValidator initialized for dungeon with dimensions " + dungeon.getWidth() + "x" + dungeon.getHeight());
    }

    public boolean isValidCoordinate(int x, int y) {
        boolean valid = x >= 0 && x < dungeon.getWidth() && y >= 0 && y < dungeon.getHeight();
        System.out.println("Valid coordinate: " + valid + " for position " + x + "," + y);
        return valid;
    }

    public boolean isValidMove(int dx, int dy, Player player) {
        if (player == null) {
            System.out.println("Cannot validate move for null player");
            return false;
        }
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;
        if (!isValidCoordinate(newX, newY)) {
            System.out.println("Move to " + newX + "," + newY + " is not valid: out of bounds");
            return false;
        }
        Tile tile = dungeon.getTile(newX, newY);
        boolean passable = tile.isPassable();
        System.out.println("Move to (" + newX + ", " + newY + ") passable: " + passable);
        return passable;
    }

    public boolean isValidInteraction(int x, int y, Player player) {
        if (player == null) {
            System.out.println("Cannot validate interaction for null player");
            return false;
        }
        if (!isValidCoordinate(x, y)) {
            System.out.println("Interaction at " + x + "," + y + " is not valid: out of bounds");
            return false;
        }
        int dx = Math.abs(x - player.getX());
        int dy = Math.abs(y - player.getY());
        boolean valid = dx + dy <= 1;
        System.out.println("Interaction at (" + x + ", " + y + ") from player at " + "(" +
                player.getX() + ", " + player.getY() + "): " + (valid ? "valid" : "invalid"));
        return valid;
    }
}
