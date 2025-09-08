package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player implements Creature {
    private final String name;
    private final int maxHealth;
    private int health;
    private int x;
    private int y;
    private final List<Item> inventory = new ArrayList<>();

    // Enkel bas-skada för attack
    private int baseDamage = 5;

    public Player(String name, int maxHealth, int startX, int startY) {
        this.name = name;
        this.maxHealth = Math.max(1, maxHealth);
        this.health = this.maxHealth;
        this.x = startX;
        this.y = startY;
    }

    // --- Creature ---
    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void takeDamage(int amount) {
        if (amount <= 0) return;
        health = Math.max(0, health - amount);
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    // --- Player-specifika metoder ---
    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void attack(Enemy enemy) {
        if (enemy == null || !enemy.isAlive()) return;
        enemy.takeDamage(baseDamage);
    }

    public void use(Item item) {
        if (item == null) return;
        item.use(this);
    }

    public void addItem(Item item) {
        if (item != null) {
            inventory.add(item);
        }
    }

    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    public void heal(int amount) {
        if (amount <= 0) return;
        health = Math.min(maxHealth, health + amount);
    }

    public void setBaseDamage(int baseDamage) {
        if (baseDamage > 0) this.baseDamage = baseDamage;
    }

    public int getBaseDamage() {
        return baseDamage;
    }
}
