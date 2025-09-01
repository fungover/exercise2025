package org.example.entities;

public abstract class  Character {
    protected String name;
    protected int health;
    protected int x;
    protected int y;

    public Character(String name, int health, int x, int y) {
        this.name = name;
        this.health = health;
        this.x = x;
        this.y = y;
    }
    public abstract void takeTurn();
    // Getters & setters
}
