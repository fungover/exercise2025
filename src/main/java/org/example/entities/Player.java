package org.example.entities;

interface CombatBehaviour {
    void attack(Enemy enemy);
    int getDamage();
    String toString();
}

public class Player {
}
