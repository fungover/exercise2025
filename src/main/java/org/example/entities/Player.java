package org.example.entities;

public class Player implements Health {
    private final String name;
    private int health = 50;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return 120;
    }
}
