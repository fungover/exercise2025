package org.example.entities.characters;

import org.example.entities.items.Inventory;
import org.example.entities.items.equippables.Weapon;
import org.example.entities.items.equippables.Armor;

public abstract class Player {
    private final String name;
    private final Inventory inventory = new Inventory();
    private int health;
    private final int maxHealth;
    private int x, y; // map coordinates (tile-based)

    protected Player(String name, int maxHealth, int startX, int startY) {
        if (maxHealth <= 0) throw new IllegalArgumentException("maxHealth must be > 0");
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.x = startX;
        this.y = startY;
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getHealth() {
        return health;
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

    public boolean isDead() {
        return health <= 0;
    }

    public Weapon getWeapon() {
        return inventory.weapon();
    }

    public Armor getArmor() {
        return inventory.armor();
    }

    public int getDefense() {
        return (inventory.armor() != null) ? inventory.armor().damageReduction() : 0;
    }

    public void heal(int amount) {
        if (amount <= 0) return;
        health = Math.min(maxHealth, health + amount);
    }

    // Damage Taken
    public void takeDamage(int incomingDamage) {
        if (incomingDamage <= 0) return;
        int net = Math.max(0, incomingDamage - getDefense());
        health = Math.max(0, health - net);
    }

    //Movement
    public void moveBy(int dx, int dy) {
        this.x += dx; this.y += dy;
    }

    public void moveTo(int nx, int ny) {
        this.x = nx; this.y = ny;
    }

    //Attack Damage
    public int attackDamage() {
        int base = baseDamage();
        Weapon w = getWeapon();
        return base + ((w != null) ? w.bonusDamage() : 0);
    }

    protected abstract int baseDamage();
    public abstract String archetype();
}
