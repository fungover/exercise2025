package org.example.entities.items.potions;

import org.example.entities.items.Potion;

public class HealthPotion extends Potion {
    public HealthPotion(String name, String description, int minHealth, int maxHealth) {
        super(name, description, minHealth, maxHealth);
    }
}
