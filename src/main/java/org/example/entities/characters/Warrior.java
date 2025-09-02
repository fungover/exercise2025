package org.example.entities.characters;

public final class Warrior extends Player {
    public Warrior(String name, int startX, int startY) {
        super(name, 24, startX, startY);
    }
    @Override protected int baseDamage() { return 4; }
    @Override public String archetype() { return "Warrior"; }
}
