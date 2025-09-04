package org.example.entities;

public class CowKing extends Enemy {

    public CowKing() {
        super("Mr Cow", 48, 14);
    }

    @Override public String getAttackMessage() {
        return "The cow King formerly known as Mr Cow attacks you with his horns!";
    }

    @Override public String getDeathMessage() {
        return "chunks of beef flies everywhere! " + this.name + " is dead";
    }
}
