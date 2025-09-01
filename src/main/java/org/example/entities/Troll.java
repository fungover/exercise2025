package org.example.entities;

public class Troll extends Enemy {
    private int health = 50;

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return 15;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return "Troll";
    }
}
