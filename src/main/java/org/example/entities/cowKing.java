package org.example.entities;

public class cowKing extends Enemy {

    public cowKing() {
        super("Mr Cow", 48, 14);
    }

    @Override public String getAttackMessage() {
        return "The cow King formerly known as Mr Cow attacks you with his horns!";
    }

    @Override public String getDeathMessage() {
        return "chunks of beef flies everywhere! " + this.name + " is dead";
    }
}
