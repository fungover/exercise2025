package org.example.entities.characters;

public final class Adventurer extends Player {
    public Adventurer(String name, int startX, int startY) {
        super(name, 20, startX, startY); // base HP for Adventurer
    }
    @Override protected int baseDamage() { return 2; }
    @Override public String archetype() { return "Adventurer"; }
}
