package org.example.entities;

public abstract class Enemy extends Character {
    public Enemy(String name, int maxHp, int baseDamage, Position position) {
        super("Troll", 18, 18, 4, position);
    }
    public abstract String getType();
}
