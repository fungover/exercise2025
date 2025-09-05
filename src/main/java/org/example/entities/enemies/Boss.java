package org.example.entities.enemies;

public final class Boss extends Enemy {
    public Boss() {
        super("Cave troll", 20, 3);
    }

    @Override public String archetype() {
        return "Cave troll, the source of evil";
    }
}
