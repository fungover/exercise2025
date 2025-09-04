package org.example.entities.enemies;

public final class Orc extends Enemy {
    public Orc() {
        super("Orc", 14, 4);
    }

    @Override public String archetype() {
        return "Orc";
    }
}
