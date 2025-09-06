package org.example.entities.behaviors;

import org.example.entities.Enemy;

public interface CombatBehaviour {
    void attack(Enemy enemy);
    int getDamage();
    String toString();
}
