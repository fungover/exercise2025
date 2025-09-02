package org.example.entities.items;

public class Weapon extends Item {
    private int damage;

    public Weapon(String name, int quantity, int damage) {
        super(name, quantity);
        this.damage = damage;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public int getQuantity() {
        return super.getQuantity();
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String displayInfo() {
        return getName() + ", Damage: " + damage;
    }
}
