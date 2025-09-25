package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Player {
    // Fields
    private final String name;
    private int health;
    private final int maxHealth;
    private int x;
    private int y;
    private final List<Item> inventory;

    // Constructor
    public Player(String name, int maxHealth, int startX, int startY) {
        this.name = name;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.x = startX;
        this.y = startY;
        this.inventory = new ArrayList<>();
    }

    // Getters
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getX() { return x; }
    public int getY() { return y; }
    public List<Item> getInventory() { return inventory; }

    //Player Methods

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void takeDamage(int amount) {
        if (amount <= 0) return;
        health -= amount;
        if (health < 0) health = 0;
    }

    public void heal(int amount) {
        if (amount <= 0) return;
        health += amount;
        if (health > maxHealth) health = maxHealth;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item){
        if (item == null) return;
        if (item instanceof Weapon) {
            throw new IllegalStateException("Weapons cannot be removed from inventory.");
        }
        inventory.remove(item);
    }

    public boolean isAlive() {
        return health > 0;
    }
}
