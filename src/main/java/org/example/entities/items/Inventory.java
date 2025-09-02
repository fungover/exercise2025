package org.example.entities.items;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        if (items.contains(item)) {
            item.addQuantity();
        } else {
            items.add(item);
        }
    }

    public void removeItem(Item item) {
        if (items.contains(item)) {
            item.removeQuantity();
        } else {
            items.add(item);
        }
    }

    public void displayInventory() {
        for (Item item : items) {
            System.out.println("-----------------------------");
            System.out.println(item.displayInfo());
        }
        System.out.println("-----------------------------");
    }

}
