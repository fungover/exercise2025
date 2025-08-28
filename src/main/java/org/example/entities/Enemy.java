package org.example.entities;

public class Enemy {
    //Fields that my subclasses will have access to
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int damage;
    protected int x;
    protected int y;

    //Constructor
    public Enemy(String name, int maxHealth, int damage, int startX, int startY)    {
        this.name = name;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.x = startX;
        this.y = startY;
    }
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }

    //Methods
    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    public boolean stillAlive() {
        return health > 0;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    // Can be overridden in subclasses
    public void attack(Player player) {
        player.takeDamage(damage);
        System.out.println(name + " attacks the player and does " + damage + " damage!");
    }
}

