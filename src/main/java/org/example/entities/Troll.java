package org.example.entities;

public class Troll extends Enemy {
    public Troll(Position position) {
        super("Troll", 18, 5, position);
    }

    @Override
    public String getType() {
        return "Troll";
    }
}
