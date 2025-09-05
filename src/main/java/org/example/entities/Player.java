package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    private int baseDamage = 6; // Hardcoded value for now
    private List<Item> inventory = new ArrayList<>();
    private int maxHealth;

    public Player(String name, int health, int x, int y) {
        super(name, health, x, y);
        this.maxHealth = health; // Starting max health
    }
    @Override
    public int getAttackDamage(){
        return baseDamage;
    }

    public int getInventoryCount() {
        return inventory.size();
    }
    public int getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(int health) {
        super.setHealth(Math.min(Math.max(0, health), maxHealth));
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

    // For move in test
    public void move(int dx, int dy, org.example.map.DungeonGrid grid) {
        int newX = getX() + dx;
        int newY = getY() + dy;

        if (newX < 0 || newX >= grid.getWidth() || newY < 0 || newY >= grid.getHeight()) {
            return;
        }
        if (grid.getTiles(newX, newY).isWall()) {
            return;
        }
        setX(newX);
        setY(newY);
    }

}
