package org.example.entities;

public class Orc extends Enemy {
    public Orc() {
        super("Orc", 50, 12);
    }

    @Override public String getAttackMessage() {
        return "The orc swings its massive axe at you!";
    }

    @Override public String getDeathMessage() {
        return "The orc falls with a crash!";
    }
}
