package org.example.entities;

import org.example.utils.Inventory;

public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private int attackDamage;
    private Inventory inventory;
    private Weapon equippedWeapon;
    private int baseAttackDamage;

    // Constructor
    public Player(String name) {
        this.name = name;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.baseAttackDamage = 5;
        this.attackDamage = baseAttackDamage;
        this.inventory = new Inventory();
        this.equippedWeapon = null;
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
    public Item findItem(String itemName) {
        return inventory.findItem(itemName);
    }

    // Equip Weapon method
    public boolean equipWeapon(String weaponName) {
        Item item = findItem(weaponName);
        if (item != null && item instanceof Weapon) {
            equippedWeapon = (Weapon) item;
            updateAttackDamage();
            System.out.println("You equipped " + item.getName() + " (+" +
                    ((Weapon) item).getDamage() + " damage)");
            return true;
        }
        return false;
    }

    // Add damage from weapon to player
    private void updateAttackDamage() {
        if (equippedWeapon != null) {
            this.attackDamage = baseAttackDamage + equippedWeapon.getDamage();
        } else {
            this.attackDamage = baseAttackDamage;
        }
    }


    // Combat method
    public int attack() {
        return attackDamage;
    }

    // Display methods
    public void showStats() {
        System.out.println("=== " + name + "'s Stats ===");
        System.out.println("Health: " + health + " / " + maxHealth);
        System.out.println("Base Attack: " + baseAttackDamage);
        if (equippedWeapon != null) {
            System.out.println("Equipped: " + equippedWeapon.getName() +
                    " (+" + equippedWeapon.getDamage() + " damage)");
        }
        System.out.println("Total Attack: " + attackDamage + " damage");
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
