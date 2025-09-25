package org.example.entities.behaviors;

import org.example.entities.Player;

public class HealingEffect implements Effect {
    private final int healthRestored;

    public HealingEffect(int healthRestored) {
        if (healthRestored < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        this.healthRestored = healthRestored;
    }

    @Override
    public void apply(Player player) {
        // for later implementation
    }

    public int getValue() {
        return healthRestored;
    }

    public String toString() {
        return "Health: " + healthRestored;
    }
}
