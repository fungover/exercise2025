package org.example.entities.behaviors;

import org.example.entities.Enemy;

public class BasicCombat implements CombatBehaviour {
    private final int damage;

    public BasicCombat(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        this.damage = damage;
    }

    @Override
    public void attack(Enemy enemy) {
        enemy.takeDamage(damage);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return "+" + damage + " damage";
    }
}
