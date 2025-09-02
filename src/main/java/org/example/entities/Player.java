package org.example.entities;

import org.example.utils.Inventory;

public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private int attackDamage;
    private Inventory inventory;

    // Constructor
    public Player(String name) {
        this.name = name;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.attackDamage = 10;
        this.inventory = new Inventory();
    }

    // Health methods
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void heal(int amount) {
        this.health += amount;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    // Inventory methods
    public boolean addItem(Item item) {
        return inventory.addItem(item);
    }
    public boolean removeItem(Item item) {
        return inventory.removeItem(item);
    }
    public boolean removeItem(String itemName) {
        return inventory.removeItem(itemName);
    }
    public Item findItem(String itemName) {
        return inventory.findItem(itemName);
    }


    // Combat method
    public int attack() {
        return attackDamage;
    }

    // Display methods
    public void showStats() {
        System.out.println("=== " + name + " Stats ===");
        System.out.println("Health: " + health + " / " + maxHealth);
        System.out.println("Attack Damage: " + attackDamage);
    }
    public void showInventory() {
        inventory.display();
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getHealth() {
        return health;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getAttackDamage() {
        return attackDamage;
    }
    public Inventory getInventory() {
        return inventory;
    }

    // Setters
    public void setHealth(int health) {
        this.health = health;
    }
    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }
}
