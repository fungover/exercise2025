package org.example.entities.enemies;

public class Goblin extends Enemy {

    public Goblin()
    {
        super("Goblin", 30, 30, 1, 5);
    }

    @Override
    public String getAttackMessage(int damage) {
        return "The " + getName() + " attacks you for " + damage + " damage!";
    }

    @Override
    public String getDeathMessage() {
        return "The " + getName() + " has been defeated!";
    }

}
