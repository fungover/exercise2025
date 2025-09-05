package org.example.entities;

public abstract class Enemy implements Health {
    private String name;
    private int health;
    private int damage;

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public String getName(){
        return name;
    }

    public int getDamage() {
        if (damage == -1) {
            damage = 0;
        }
        return damage;
    }
}

