package org.game.entities;

import org.game.utils.Colors;

import java.util.HashMap;
import java.util.Map;


public class Player extends Character {
private Map<String,Item> inventory = new HashMap<>();

    public Player(String name, int x, int y) {
    super(
            name,
            20,
            10,
            5,
            5,
            5,
            5,
            5,
            x,
            y);

    }

    public void addItem(Item item) {
        if (inventory.containsKey(item.getName())) {
            Item existingItem = inventory.get(item.getName());
            existingItem.addQuantity(item.getQuantity());
            System.out.println("Picked up " + item.getQuantity() + " x " + item.getName());
        } else {
            inventory.put(item.getName(), item);
            System.out.println("Picked up " + item.getQuantity() + " x " + item.getName());
        }
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("No items in your inventory");

        } else {
            System.out.println(Colors.cyanColor+ "==============Inventory====================");
            System.out.println("You have " + inventory.size() + " items in your inventory");
           for (Item item : inventory.values()) {
               System.out.println(Colors.cyanColor + " * " + item.getName() + " x " + item.getQuantity() + Colors.resetColor);
           }

        }
    }
}
