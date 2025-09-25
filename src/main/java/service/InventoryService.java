package service;

import entities.Item;
import entities.Player;
import entities.HealingPotion;
import entities.Weapon;
import utils.Printer;

public class InventoryService {
    public static final int DEFAULT_MAX_SLOTS = 8;

    public static boolean addItem(Player player, Item item, int maxSlots) {
        if (player == null) {
        Printer.error("No player present.");
        return false;
        }
        if (item == null) {
        Printer.error("Cannot pick up a null item.");
        return false;
        }
        if (player.getInventory().size() >= maxSlots) {
            Printer.error("Inventory is full (" + maxSlots + " slots).");
            return false;
        }
        player.addItem(item);
        Printer.info("Picked up: " + item.getName());
        return true;
    }

    public static void useItemByIndex(Player player, int index) {
        if (player == null) {
        Printer.error("No player present.");
            return;
        }
        if (index < 0 || index >= player.getInventory().size()) {
            Printer.error("Invalid inventory index.");
            return;
        }
        Item item = player.getInventory().get(index);
        item.use(player);
        if (isConsumable(item)) {
            // remove by index to avoid shifting issues after equals()
            player.removeItem(item);
        }
        Printer.info("Used: " + item.getName());
    }

    public static void useItemByName(Player player, String namePart) {
        if (player == null) {
            Printer.error("No player present.");
            return;
        }
        if (namePart == null || namePart.isEmpty()) {
            Printer.error("Specify an item name.");
            return;
        }
        // Index-based search to avoid ConcurrentModificationException
        int foundIndex = -1;
        for (int i = 0; i < player.getInventory().size(); i++) {
            Item it = player.getInventory().get(i);
            if (it.getName().toLowerCase().contains(namePart.toLowerCase())) {
                foundIndex = i;
                break;
            }
        }
        if (foundIndex == -1) {
            Printer.error("Item not found: " + namePart);
            return;
        }

        Item item = player.getInventory().get(foundIndex);
        item.use(player);
        if (isConsumable(item)) {
            player.removeItem(item); // safe: not inside for-each
        }
        Printer.info("Used: " + item.getName());
    }

    private static boolean isConsumable(Item item) {
        return (item instanceof HealingPotion) || (item instanceof Weapon);
    }
}
