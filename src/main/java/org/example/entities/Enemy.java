package org.example.entities;

public class Enemy {
    private String name;
    private int health;
    private int damage;
    private int x, y;

    //Constructor
    public Enemy(String name, int health, int damage, int StartX, int StartY) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.x = StartX;
        this.y = StartY;
    }

    //Getters
    public String getName() {
        return name;
    }
    public int getHealth() {
        return health;
    }
    public int getDamage() {
        return damage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
