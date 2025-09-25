package org.example.entities;

public class Enemy {
    private final String name;
    private int hp;
    private final int damage;
    private final int xpReward;

    public Enemy(String name, int hp, int damage, int xpReward) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must be non-empty");
        if (hp < 0) throw new IllegalArgumentException("hp must be >= 0");
        if (damage < 0) throw new IllegalArgumentException("damage must be >= 0");
        if (xpReward < 0) throw new IllegalArgumentException("xpReward must be >= 0");
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
        if (amount < 0) {
        throw new IllegalArgumentException("damage amount must be >= 0");
    }
        hp = Math.max(0, hp - amount);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean isDead() {
        return hp <= 0;
    }
}