package org.example.entities;

public class Goblin extends Enemy {


    public Goblin() {
        super("Goblin", 30, 5);
    }

    @Override
    public String getAttackMessage() {
        //if we have more special goblins use getName()
        return "The goblin swipes at you with its rusty dagger!";
    }

    @Override
    public String getDeathMessage() {
        return "The goblin collapses with a final bloody screech";
    }
}
