package org.example.entities.enemies;

import org.example.entities.behaviors.BasicAttack;
import org.example.entities.Enemy;

public class Goblin extends Enemy {
    public Goblin() {
        super("Goblin", 10, new BasicAttack(5));
    }
}