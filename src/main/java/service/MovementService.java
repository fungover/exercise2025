package service;

import entities.Player;
import map.PirateCave;
import game.InputHandler.Direction;

/**
 * MovementService handles all movement logic for the player
 *
 * @author Jörgen Lindström
 * @version 1.0
 */
public class MovementService {

    public MovementResult movePlayer(Player player, PirateCave cave, Direction direction) {
        // Calculate new position
        int currentX = player.getX();
        int currentY = player.getY();
        int newX = currentX + direction.getDeltaX();
        int newY = currentY + direction.getDeltaY();

        // Check if the movement is valid
        if (!cave.canPlayerMoveTo(newX, newY)) {
            return new MovementResult(false,
                    "Du kan inte gå " + direction.getDescription() + " - vägen är blockerad!",
                    currentX, currentY);
        }

        // Perform the movement
        player.setPosition(newX, newY);

        String message = "Du går " + direction.getDescription() + ".";
        return new MovementResult(true, message, newX, newY);
    }

     // Checks if a movement is possible without performing it
    public boolean canMove(Player player, PirateCave cave, Direction direction) {
        int newX = player.getX() + direction.getDeltaX();
        int newY = player.getY() + direction.getDeltaY();
        return cave.canPlayerMoveTo(newX, newY);
    }


     //Returns all possible directions the player can go
    public java.util.List<Direction> getAvailableDirections(Player player, PirateCave cave) {
        java.util.List<Direction> available = new java.util.ArrayList<>();

        for (Direction dir : Direction.values()) {
            if (canMove(player, cave, dir)) {
                available.add(dir);
            }
        }

        return available;
    }

     // Results of a movement attempt
    public static class MovementResult {
        private final boolean success;
        private final String message;
        private final int finalX;
        private final int finalY;

        public MovementResult(boolean success, String message, int finalX, int finalY) {
            this.success = success;
            this.message = message;
            this.finalX = finalX;
            this.finalY = finalY;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public int getFinalX() { return finalX; }
        public int getFinalY() { return finalY; }
    }
}