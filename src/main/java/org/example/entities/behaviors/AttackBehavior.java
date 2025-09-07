package org.example.entities.behaviors;

import org.example.entities.Player;

public interface AttackBehavior {
    void attack(Player player);
    int getDamage();
    String toString();
}