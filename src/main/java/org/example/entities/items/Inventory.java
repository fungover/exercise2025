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

    public Weapon getWeapon() {
        for (Item item : items) {
            if (item instanceof Weapon) {
                return (Weapon) item;
            }
        }
        return null;
    }

    public HealthPotion getHealthPotion() {
        for (Item item : items) {
            if (item instanceof HealthPotion) {
                return (HealthPotion) item;
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
