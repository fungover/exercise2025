package org.example.entities;

import java.util.ArrayList;
import java.util.List;

interface CombatBehaviour {
    void attack(Enemy enemy);
    int getDamage();
    String toString();
}

public class Player {
    private final String name;
    private int health;
    private int baseDamage;
    private final List<Item> inventory;
    private int x;
    private int y;
    private final CombatBehaviour combatBehaviour;

    public Player(String name, int health, int baseDamage, CombatBehaviour combatBehaviour) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        if (health <= 0) {
            throw new IllegalArgumentException("Health cannot be negative or zero");
        }
        if (baseDamage < 0) {
            throw new IllegalArgumentException("Base damage cannot be negative");
        }
        if (combatBehaviour == null) {
            throw new IllegalArgumentException("Combat behaviour cannot be null");
        }
        this.name = name;
        this.health = health;
        this.baseDamage = baseDamage;
        this.combatBehaviour = combatBehaviour;
        this.inventory = new ArrayList<>();
        this.x = 0;
        this.y = 0;
    }
}
