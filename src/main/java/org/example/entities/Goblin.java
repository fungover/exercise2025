package org.example.entities;

public class Goblin extends Enemy {
    private int health = 30;

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return 5;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return "Goblin";
    }

}
