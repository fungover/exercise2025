package org.SpinalGlitter.exercise2.entities;

import org.SpinalGlitter.exercise2.utils.WorldObject;

public class Weapon implements WorldObject {
    private final String name;
    private final int damage;
    private final Position position;

    public Weapon(String name, int damage, Position position) {
        this.name = name;
        this.damage = damage;
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String getSymbol() {
        return "⚔️";
    }
}
