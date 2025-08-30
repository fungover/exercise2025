package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private int x, y; //position
    private int damage;
    private List<Item> inventory;

    public Player(String name) {
        this.name = name;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.damage = 10;
        this.inventory = new ArrayList<>();
    }

    //getters

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
        return damage;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    //setters
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, this.health - damage);
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    //methods
}
