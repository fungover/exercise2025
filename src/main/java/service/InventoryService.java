package service;

import entities.Player;
import entities.Item;

/**
 * InventoryService hanterar all inventory-relaterad logik
 *
 * @author Jörgen Lindström
 * @version 1.0
 */
public class InventoryService {

    public InventoryResult addItem(Player player, Item item) {
        if (!player.addItem(item)) {
            return new InventoryResult(false,
                    "Ditt inventory är fullt! (" + player.getMaxInventorySize() + " föremål max)",
                    player.getInventory().size());
        }

        String message = "Du lade " + item.getName() + " i ditt inventory.";
        return new InventoryResult(true, message, player.getInventory().size());
    }

    public String getInventoryDisplay(Player player) {
        return player.showInventory();
    }

    public static class InventoryResult {
        private final boolean success;
        private final String message;
        private final int currentSize;

        public InventoryResult(boolean success, String message, int currentSize) {
            this.success = success;
            this.message = message;
            this.currentSize = currentSize;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public int getCurrentSize() { return currentSize; }
    }
}