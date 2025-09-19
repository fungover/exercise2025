package org.example.entities;

public class Enemy {
    private String name;
    private int health;
    private int damage;
    private boolean isAlive;

    public Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.isAlive = true;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
        }
    }

    public int attack() {
        return this.damage;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isAlive() {
        return isAlive;
    }
}