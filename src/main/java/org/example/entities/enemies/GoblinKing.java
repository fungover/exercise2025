package org.example.entities.enemies;

public class GoblinKing extends Enemy {

    public GoblinKing() {
        super("Goblin King", 100, 100, 2, 15); // stronger stats
    }

    @Override
    public String getAttackMessage() {
        return "The mighty " + getName() + " slashes you for " + getDamage() + " damage!";
    }

    @Override
    public String getDeathMessage() {
        return "The " + getName() + " has been defeated!";
    }
}
