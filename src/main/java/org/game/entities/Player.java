package org.game.entities;

import java.util.List;
import java.util.ArrayList;


public class Player extends Character {
private List<Item> inventory = new ArrayList<>();

    public Player(String name, int x, int y) {
    super(name,100,15,5,5,5,5,5,x,y);

    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println("Picked up " + item.getName());
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("No items in your inventory");

        }else {
            System.out.println("Items in your inventory:");
            inventory.forEach(i-> System.out.println("- " + i.getName()));

        }
    }
}
