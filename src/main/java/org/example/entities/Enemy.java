package org.example.entities;

public abstract class Enemy implements Health, Position {
    private String name;
    private int health;
    private int damage;
    private int x;
    private int y;

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

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

