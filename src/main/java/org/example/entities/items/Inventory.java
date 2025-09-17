package org.example.entities.items;

import java.util.ArrayList;

public class Inventory {
    private final ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        for (Item existingItem : items) {
            if (existingItem.equals(item)) {
                existingItem.addQuantity();
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(Item item) {
        for (Item existingItem : items) {
            if (existingItem.equals(item) && existingItem.getQuantity() > 1) {
                existingItem.removeQuantity();
                return;
            }
        }
        items.remove(item);
    }

    public <T extends Item> T getItem(Class<T> itemClass) {
        for (Item item : items) {
            if (itemClass.isInstance(item)) {
                return itemClass.cast(item);
            }
        }
        return null;
    }

    public void displayInventory() {
        for (Item item : items) {
            System.out.println("-----------------------------");
            System.out.println(item.displayInfo());
        }
        System.out.println("-----------------------------");
    }

}
