package org.example.service;

import org.example.entities.items.Potion;
import org.example.entities.items.Weapon;

public class DifficultyConfig {
    public final String difficulty;
    public final int health;
    public final int maxHealth;
    public final Weapon startingWeapon;
    public final Potion startingPotion;

    public DifficultyConfig(String difficulty, int health, int maxHealth, Weapon startingWeapon, Potion startingPotion) {
        this.difficulty = difficulty;
        this.health = health;
        this.maxHealth = maxHealth;
        this.startingWeapon = startingWeapon;
        this.startingPotion = startingPotion;
    }
}
