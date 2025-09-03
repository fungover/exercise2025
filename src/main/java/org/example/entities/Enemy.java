package org.example.entities;

public class Enemy {
    private String name;
    private int hp;
    private int damage;

    public Enemy(String name, int hp, int damage) {
        this.name = name;
        this.hp = hp;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int amount) {
        hp -= amount;
    }

    public boolean isAlive() {
        return hp > 0;
    }
}
