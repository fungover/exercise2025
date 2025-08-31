package org.example.entities.enemies;

public class Goblin extends Enemy {

    public Goblin(int x, int y) {
        super("Goblin", 30, 30, x, y, 5);
    }

    @Override
    public String getAttackMessage() {
        return "The " + getName() + " attacks you for " + getDamage();
    }

    @Override
    public String getDeathMessage() {
        return "The " + getName() + " dies";
    }

}
