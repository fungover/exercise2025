package org.example.entities;

public class Goblin extends Enemy {
    private int health = 20;

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return "Goblin";
    }

}
