package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    private int baseDamage = 6; // Hardcoded value for now
    private List<Item> inventory = new ArrayList<>();

    public Player(String name, int health, int x, int y) {
        super(name, health, x, y);
    }
    @Override
    public int getAttackDamage(){
        return baseDamage;
    }

    public int getInventoryCount() {
        return inventory.size();
    }

    @Override
    public void takeTurn() {
        // Player takes turn logic
        System.out.println(name + " waits for your command...");
    }

    // Inventory logic
    public void addItem(Item item) {
        inventory.add(item);
        System.out.println(item.getName() + " added to your inventory!");
    }
    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You have no items in your inventory!");
        } else {
            System.out.println("Your inventory:");
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println((i + 1) + ". " + inventory.get(i).getName());
            }
        }
    }
    public void useItem(int index) {
        if (index >= 0 && index < inventory.size()) {
            Item item = inventory.remove(index);
            item.use(this);
        } else {
            System.out.println("Invalid item index!");
        }
    }

}
