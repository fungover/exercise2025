package org.example.entities.enemies;

public final class Goblin extends Enemy {
    public Goblin() {
        super("Goblin", 8, 3);
    }

    @Override public String archetype() {
        return "Goblin";
    }
}
