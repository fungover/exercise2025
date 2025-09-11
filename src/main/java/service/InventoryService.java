package service;

import entities.Player;
import entities.Item;
import java.util.List;

/**
 * InventoryService hanterar all inventory-relaterad logik
 * Förenklad version som fungerar med våra befintliga klasser
 *
 * @author Jörgen Lindström
 * @version 1.0
 */
public class InventoryService {

    /**
     * Lägger till ett föremål i spelarens inventory
     * @param player Spelaren
     * @param item Föremålet att lägga till
     * @return InventoryResult med information om resultatet
     */
    public InventoryResult addItem(Player player, Item item) {
        if (!player.addItem(item)) {
            return new InventoryResult(false,
                    "Ditt inventory är fullt! (" + player.getMaxInventorySize() + " föremål max)",
                    player.getInventory().size());
        }

        String message = "Du lade " + item.getName() + " i ditt inventory.";
        return new InventoryResult(true, message, player.getInventory().size());
    }

    /**
     * Tar bort ett föremål från inventory
     * @param player Spelaren
     * @param itemName Namnet på föremålet att ta bort
     * @return InventoryResult med information om resultatet
     */
    public InventoryResult removeItem(Player player, String itemName) {
        Item foundItem = player.findItem(itemName);

        if (foundItem == null) {
            return new InventoryResult(false,
                    "Du har inget föremål som heter '" + itemName + "'",
                    player.getInventory().size());
        }

        player.removeItem(foundItem);
        String message = "Du tog bort " + foundItem.getName() + " från ditt inventory.";

        return new InventoryResult(true, message, player.getInventory().size());
    }

    /**
     * Visar en formaterad lista över inventory
     * @param player Spelaren
     * @return Formaterad sträng med inventory-innehåll
     */
    public String getInventoryDisplay(Player player) {
        return player.showInventory();
    }

    /**
     * Kontrollerar om inventory är fullt
     */
    public boolean isInventoryFull(Player player) {
        return player.getInventory().size() >= player.getMaxInventorySize();
    }

    /**
     * Returnerar antal lediga platser i inventory
     */
    public int getAvailableSlots(Player player) {
        return player.getMaxInventorySize() - player.getInventory().size();
    }

    /**
     * Kontrollerar om spelaren har ett specifikt föremål
     */
    public boolean hasItem(Player player, String itemName) {
        return player.findItem(itemName) != null;
    }

    /**
     * Returnerar inventory-statistik
     */
    public String getInventoryStats(Player player) {
        List<Item> inventory = player.getInventory();

        StringBuilder sb = new StringBuilder();
        sb.append("=== INVENTORY-STATISTIK ===\n");
        sb.append("Totalt: ").append(inventory.size()).append("/").append(player.getMaxInventorySize()).append(" föremål\n");
        sb.append("Lediga platser: ").append(getAvailableSlots(player)).append("\n\n");

        sb.append("Föremål:\n");
        for (int i = 0; i < inventory.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(inventory.get(i).getName()).append("\n");
        }

        return sb.toString();
    }

    /**
     * Resultat av en inventory-operation
     */
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