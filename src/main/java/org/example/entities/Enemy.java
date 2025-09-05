package org.example.entities;

interface AttackBehavior {
    void attack(Player player);
    int getDamage();
    String toString();
}

public abstract class Enemy {
    private final String name;
    private int health;
    private final AttackBehavior attackBehavior;

    protected Enemy(String name, int health, AttackBehavior attackBehavior) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Enemy name cannot be null or empty");
        }
        if (health <= 0) {
            throw new IllegalArgumentException("Health cannot be negative or zero");
        }
        if (attackBehavior == null) {
            throw new IllegalArgumentException("Attack behavior cannot be null");
        }
        this.name = name;
        this.health = health;
        this.attackBehavior = attackBehavior;
    }
}

