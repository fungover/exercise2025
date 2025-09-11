package service;

import entities.Player;
import map.PirateCave;
import game.InputHandler.Direction;

/**
 * MovementService hanterar all rörelselogik för spelaren
 *
 * @author Jörgen Lindström
 * @version 1.0
 */
public class MovementService {

    /**
     * Försöker flytta spelaren i en viss riktning
     * @param player Spelaren som ska flyttas
     * @param cave Kartan spelaren befinner sig på
     * @param direction Riktningen att flytta åt
     * @return MovementResult med information om rörelsen
     */
    public MovementResult movePlayer(Player player, PirateCave cave, Direction direction) {
        // Beräkna ny position
        int currentX = player.getX();
        int currentY = player.getY();
        int newX = currentX + direction.getDeltaX();
        int newY = currentY + direction.getDeltaY();

        // Kontrollera om rörelsen är giltig
        if (!cave.canPlayerMoveTo(newX, newY)) {
            return new MovementResult(false,
                    "Du kan inte gå " + direction.getDescription() + " - vägen är blockerad!",
                    currentX, currentY);
        }

        // Utför rörelsen
        player.setPosition(newX, newY);

        String message = "Du går " + direction.getDescription() + ".";
        return new MovementResult(true, message, newX, newY);
    }

    /**
     * Kontrollerar om en rörelse är möjlig utan att utföra den
     */
    public boolean canMove(Player player, PirateCave cave, Direction direction) {
        int newX = player.getX() + direction.getDeltaX();
        int newY = player.getY() + direction.getDeltaY();
        return cave.canPlayerMoveTo(newX, newY);
    }

    /**
     * Returnerar alla möjliga riktningar spelaren kan gå
     */
    public java.util.List<Direction> getAvailableDirections(Player player, PirateCave cave) {
        java.util.List<Direction> available = new java.util.ArrayList<>();

        for (Direction dir : Direction.values()) {
            if (canMove(player, cave, dir)) {
                available.add(dir);
            }
        }

        return available;
    }

    /**
     * Resultat av ett rörelseförsök
     */
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