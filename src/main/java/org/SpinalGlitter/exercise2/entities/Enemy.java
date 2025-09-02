package org.SpinalGlitter.exercise2.entities;

public class Enemy {
    private String name;
    private int hp;
    private int damage;
    private Position position;

    public Enemy(String name, int hp, int damage, Position position) {
        this.name = name;
        this.hp = hp;
        this.damage = damage;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    int getDamage() {
        return damage;
    }

    public boolean isAlive() {
        return hp > 0;
    }
    public Position getPosition() {
        return position;
    }
}

// Type
