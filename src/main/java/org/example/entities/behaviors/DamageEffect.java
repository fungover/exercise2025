package org.example.entities.behaviors;

import org.example.entities.Player;

public class DamageEffect implements Effect {
    private final int damage;

    public DamageEffect(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        this.damage = damage;
    }

    @Override
    public void apply(Player player) {
        // for later implementation
    }

    @Override
    public int getValue() {
        return damage;
    }

    @Override
    public String toString() {
        return "Damage: " + damage;
    }
}
