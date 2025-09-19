package org.example.entities.items;

public class Weapon extends Item {

    public Weapon(String name, int damage) {
        super(name, "weapon", damage);
    }

    public int getDamage() {
        return getEffect();
    }
}
