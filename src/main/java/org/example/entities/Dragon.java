package org.example.entities;

public class Dragon extends Enemy {
    private int health = 70;

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void increaseHealth(int amount) {
        this.health += amount;
    }

    @Override
    public void decreaseHealth(int amount) {
        this.health -= amount;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return 30;
    }

    public String getName() {
        return "Dragon";
    }
}
