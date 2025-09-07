package org.example.entities.enemies;

import org.example.entities.Enemy;
import org.example.entities.behaviors.BasicAttack;

public class Troll extends Enemy {
    public Troll() {
        super("Troll", 10, new BasicAttack(10));
    }
}