package org.example.utils;

import org.example.entities.Item;

import java.util.ArrayList;
import java.util.List;

import static org.example.utils.Colors.green;

public class Inventory {
    private List<Item> items;
    private int maxCapacity;

    //Constructor
    public Inventory() {
        this(10); // Default capacity
    }

    public Inventory(int maxCapacity) {
        this.items = new ArrayList<>();
        this.maxCapacity = maxCapacity;
    }

    // Item management
    public boolean addItem(Item item) {
        if (items.size() < maxCapacity) {
            items.add(item);
            return true;
        }
        return false;
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public boolean removeItem(String itemName) {
        Item item = findItem(itemName);
        if (item != null) {
            return items.remove(item);
        }
        return false;
    }

    public Item findItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public boolean hasItem(String itemName) {
        return findItem(itemName) != null;
    }

    // Utility methods
    public boolean isEmpty() {
        return items.isEmpty();
    }
    public boolean isFull() {
        return items.size() >= maxCapacity;
    }
    public int getSize() {
        return items.size();
    }
    public int getCapacity() {
        return maxCapacity;
    }

    // Display methods
    public void display() {
        System.out.println(green("\n========================= Inventory (" + items.size() + "/" + maxCapacity +
                ") ========================="));
        if (items.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.print(green("| " + (i + 1) + ". "));
                items.get(i).displayInfo();
            }
        }
        System.out.println(green("===================================================================="));
        if (items.size() == maxCapacity) {
            System.out.println("\nYour inventory is full!");
        }
    }

    // Getters
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
