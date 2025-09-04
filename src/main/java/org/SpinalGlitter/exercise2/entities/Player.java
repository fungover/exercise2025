package org.SpinalGlitter.exercise2.entities;

public class Player {

    private final String name;
    private final int maxHealth;
    private int currentHealth;
    private int damage;
    private Position position;
    private final Inventory inventory = new Inventory(20);

    public Player(String name) {
        this.name = name;
        this.maxHealth = 100;
        this.damage = 10;
        this.currentHealth = 10;
        this.position = new Position(1, 1);

    }

    public void setDamage(int damage) {
        this.damage += damage;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean haveWeapon() {
        return inventory.hasWeapon();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void takeDamage(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
            currentHealth = 0;
        }

    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public Position getPosition() {
        return position;
    }

    public void move(int dx, int dy) {
        this.position = position.getAdjacent(dx, dy);
    }

    public void heal(int amount) {
        if (!inventory.hasPotion()) {
            System.out.println("No items in inventory to heal.");

        } else if (currentHealth == maxHealth) {
            System.out.println("Health is already full.");
        } else {
            inventory.consumeFirstPotion();
            currentHealth += amount;
            if (currentHealth > maxHealth) {
                currentHealth = maxHealth;
            }
        }
    }
}
