package org.example.entities.behaviors;

import org.example.entities.Player;

public class BasicAttack implements AttackBehavior {
    private final int damage;

    public BasicAttack(int damage) {
        if (damage <= 0) {
            throw new IllegalArgumentException("Damage must be positive");
        }
        this.damage = damage;
    }

    @Override
    public void attack(Player player) {
        player.takeDamage(damage);
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