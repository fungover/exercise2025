package org.example.entities;

public class Enemy {
    private String name;
    private int hp;
    private int damage;
    private int xpReward;

    public Enemy(String name, int hp, int damage, int xpReward) {
        this.name = name;
        this.hp = hp;
        this.damage = damage;
        this.xpReward = xpReward;
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

    public int getXpReward() {
        return xpReward;
    }

    public void takeDamage(int amount) {
        hp -= amount;
        if (hp < 0) {
            hp = 0;
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean isDead() {
        return hp <= 0;
    }
}