package org.example.entities;

public class Enemy implements Health {
    private int health;
    private int damage;

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        if (damage == -1) {
            damage = 0;
        }
        return damage;
    }
}

