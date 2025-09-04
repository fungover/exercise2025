package org.SpinalGlitter.exercise2.entities;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<ItemObject> items;
    // TODO: create a object interface to store items in. Should be declared List<interface> items
    private final int capacity;

    public Inventory(int capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }
    /** Add an item to the inventory if there is space.
     * @param item The item to add.
     * @return true if the item was added, false if the inventory is full.
     */
    public boolean addItem(ItemObject item) {
        if (isFull()) return false;
        items.add(item);
        return true;
    }

    public  boolean hasWeapon() {
        for (ItemObject it : items) {
            if (it instanceof Sword) return true;
        }
        return false;
    }

    // for testing purposes
    public List<ItemObject> getItems() {
        return items;
    }

    public void removeWeapon() {
            for (int i = 0; i < items.size(); i++) {
                ItemObject it = items.get(i);
                if (it instanceof Sword) {
                    items.remove(i);
                    System.out.println("You threw away your weapon...");
                    return;
                    }
                }
            System.out.println("You don't have a weapon to throw away.");
            }


    public void consumeFirstPotion() {
    for (int i = 0; i < items.size(); i++) {
        ItemObject it = items.get(i);
        if (it instanceof Potion) {
            items.remove(i);
            break;
        }
    }
}

    public boolean isFull() {
        return items.size() >= capacity;
    }

    // show inventory items
    public void printItems() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Inventory (" + items.size() + "/" + capacity + "):");
            for (int i = 0; i < items.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + items.get(i).printItem());
            }
        }
    }

    public boolean hasPotion() {
        for (ItemObject it : items) {
            if (it instanceof Potion) return true;
        }
        return false;
    }
}
