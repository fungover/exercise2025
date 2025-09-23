package org.example.entities;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(String itemName) {
        items.removeIf(item -> item.getName().equals(itemName));
    }

    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("No items in Inventory");
        } else {
            System.out.println("Items in Inventory:");
            for (Item item : items) {
                System.out.println("You have: " + item);
            }
        }
    }

    public boolean hasItem(String itemName) {
        if (!items.isEmpty()) {
            for (Item item : items) {
                if (item.getName().equals(itemName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
