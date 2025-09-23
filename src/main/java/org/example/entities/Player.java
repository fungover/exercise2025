package org.example.entities;

public class Player {
    private String name;
    private int damage;
    private int health;
    private int maxHealth;
    private int positionX;
    private int positionY;
    private Inventory inventory;

    public Player (String name,int damage,  int health, int maxHealth) {
        this.name = name;
        this.damage = damage;
        this.health = health;
        this.maxHealth = maxHealth;
        this.positionX = 0;
        this.positionY = 0;
    }

    public String getName () {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
