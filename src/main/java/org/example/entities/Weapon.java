package org.example.entities;

public class Weapon extends Item {

    public Weapon(String name, int damage) {
        super(name, "weapon", damage);
    }

    public int getDamage() {
        return getEffect();
    }
}
