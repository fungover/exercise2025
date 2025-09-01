package org.example.entities.characters;

import org.example.entities.enemies.Enemy;
import org.example.entities.enemies.Goblin;
import org.example.entities.items.Inventory;
import org.example.entities.items.Item;
import org.example.entities.items.Weapon;
import org.example.utils.RandomUtils;

public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private int x, y;
    private Inventory inventory;
    private Weapon equippedWeapon;

    public Player(String name, int health, int maxHealth, int x, int y, int damage, Weapon equippedWeapon) {
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

    public Weapon getEquippedWeapon(){return equippedWeapon;}

    public Inventory getInventory() {
        return inventory;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
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

    public String setEquippedWeapon(Weapon weapon){
        this.equippedWeapon = weapon;
        return getName() + " equipped " + weapon.getName();
    }

    //Methods
    public String getAttackMessage(Enemy enemy) {
        return getName() + " attacks " + enemy.getName() + " for " + getDamage() + " damage!";
    }

    public String equipWeapon (Weapon weapon) {
        return getName() + " equipped " + weapon.getName();
    }

    public String getDeathMessage(Enemy enemy) {
        return getName() + " dies after getting struck for " + enemy.getDamage() + " damage!";
    }

    public String getHealMessage() {
        return getName() + " heals for 10 health!";
    }

    public String getLoseHealthMessage(int amount) {
        return getName() + " loses " + amount + " health!";
    }
}