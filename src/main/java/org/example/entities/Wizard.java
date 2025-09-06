package org.example.entities;

public class Wizard extends Enemy {
    public Wizard (Position position) {
        super("Wizard", 14, 3, position);
    }

    @Override
    public String getType() {
        return "Wizard";
    }
}
