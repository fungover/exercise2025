package org.example.entities.characters;

import org.example.entities.enemies.Enemy;
import org.example.entities.items.Inventory;
import org.example.entities.items.Item;
import org.example.entities.items.Potion;
import org.example.entities.items.Weapon;
import org.example.utils.RandomUtils;

public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private int x, y;
    private Inventory inventory;
    private Weapon equippedWeapon;

    public Player(String name, int health, int maxHealth, int x, int y, Weapon equippedWeapon) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.x = x;
        this.y = y;
        this.inventory = new Inventory();
        this.equippedWeapon = equippedWeapon;
    }

    //Getters
    public String getName() {
        return name;
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

    public int getDamage() {
        if (equippedWeapon != null) {
            return equippedWeapon.getDamage();
        } else {
            return RandomUtils.getRandomNumber(1, 4);
        }
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Inventory getInventory() {
        return inventory;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, this.maxHealth));
    }

    public void setMaxHealth(int maxHealth) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Max health must be greater than 0");
        }
        this.maxHealth = maxHealth;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setEquippedWeapon(Weapon newWeapon) {
        if (newWeapon == null) {
            throw new IllegalArgumentException("newWeapon cannot be null");
        }
        if (equippedWeapon != null) {
            inventory.addItem(equippedWeapon);
        }
        System.out.println("You equipped " + newWeapon.getName() + "!");
        this.equippedWeapon = newWeapon;
    }

    //Methods
    public void takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage must be greater than or equal to 0");
        }

        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
        }
    }

    public void usePotion(Potion potion) {
        if (potion != null) {
            int rolled = potion.getValue();
            int before = this.health;
            this.health = Math.min(this.health + rolled, this.maxHealth);
            int restored = this.health - before;
            System.out.println("> You use a " + potion.getName() + " and restore " + restored + " health!");
            inventory.removeItem(potion);
        }
    }

    public void addToInventory(Item item) {
        inventory.addItem(item);
    }

    public String getAttackMessage(Enemy enemy) {
        return "> " + getName() + " attacks " + enemy.getName() + " for " + getDamage() + " damage!";
    }

    public String getDeathMessage(Enemy enemy) {
        return "> " + getName() + " dies after getting struck for " + enemy.getDamage() + " damage!";
    }

}