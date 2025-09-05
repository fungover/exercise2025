package org.example.entities;

public class Dragon extends Enemy {
    private int health = 70;

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return 30;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return "Dragon";
    }
}
