package org.example.entities;

public abstract class Enemy extends Character {
    public Enemy(String name, int maxHp, int baseDamage, Position position) {
        super(name, maxHp, baseDamage, position);
    }
    public abstract String getType();
}
