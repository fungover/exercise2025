package org.example.entities.characters;

import org.example.entities.items.Inventory;
import org.example.entities.items.equippables.Weapon;
import org.example.entities.items.equippables.Armor;

public abstract class Player {
    private final String name;
    private final Inventory inventory = new Inventory();
    private int health;
    private int maxHealth;
    private int x, y; // map coordinates (tile-based)

    protected Player(String name, int maxHealth, int startX, int startY) {
        if (maxHealth <= 0) throw new IllegalArgumentException("maxHealth must be > 0");
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.x = startX;
        this.y = startY;
    }

    public String getName() { return name; }
    public Inventory getInventory() { return inventory; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isDead() { return health <= 0; }
    public Weapon getWeapon() { return inventory.weapon(); }
    public Armor getArmor()   { return inventory.armor(); }
    public int getDefense()   { return (inventory.armor() != null) ? inventory.armor().damageReduction() : 0; }

    public void heal(int amount) {
        if (amount > 0)
            //Implement later
            System.out.println("health + amount");
            System.out.println("MaxHealth is cap (no overhealing)");
    }

    // Damage Taken
    public void takeDamage(int amount) {
        if (amount <= 0) return;
        //Implement later
        System.out.println("netDamage = incomingDamage - defence");
        System.out.println("health - netDamage");
    }

    //Max Health increase
    public void increaseMaxHealth(int increment) {
        if (increment > 0) {
            //Implement later
            System.out.println("maxHealth + increment");
        }
    }

    //Movement
    public void moveBy(int dx, int dy) {
        this.x += dx; this.y += dy;
    }

    public void moveTo(int nx, int ny) {
        this.x = nx; this.y = ny;
    }

    //Attack Damage
    public void attackDamage() {
        //Implement later
        System.out.println("baseDamage + weaponDamage");
    }

    protected abstract int baseDamage();
    public abstract String archetype();
}
