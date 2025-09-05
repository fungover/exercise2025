package service;

import entities.Item;
import entities.Player;
import entities.HealingPotion;
import entities.Weapon;
import utils.Printer;

public class InventoryService {
    public static final int DEFAULT_MAX_SLOTS = 8;

    /** Försök lägga till ett item om plats finns. */
    public static boolean addItem(Player player, Item item, int maxSlots) {
        if (player.getInventory().size() >= maxSlots) {
            Printer.error("Inventariet är fullt (" + maxSlots + " platser).");
            return false;
        }
        player.addItem(item);
        Printer.info("Du plockade upp: " + item.getName());
        return true;
    }

    /** Använd item via index (0-baserat). */
    public static void useItemByIndex(Player player, int index) {
        if (index < 0 || index >= player.getInventory().size()) {
            Printer.error("Ogiltigt index.");
            return;
        }
        Item item = player.getInventory().get(index);
        item.use(player);
        if (isConsumable(item)) {
            player.removeItem(item);
        }
        Printer.info("Använde: " + item.getName());
    }

    /** Använd item genom att matcha namn.*/
    public static void useItemByName(Player player, String namePart) {
        for (Item item : player.getInventory()) {
            if (item.getName().toLowerCase().contains(namePart.toLowerCase())) {
                item.use(player);
                if (isConsumable(item)) {
                    player.removeItem(item);
                }
                Printer.info("Använde: " + item.getName());
                return;
            }
        }
        Printer.error("Item hittades inte: " + namePart);
    }

    /** Potion och Weapon är engångs. */
    private static boolean isConsumable(Item item) {
        return (item instanceof HealingPotion) || (item instanceof Weapon);
    }
}
