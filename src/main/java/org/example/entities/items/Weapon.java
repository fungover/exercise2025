package org.example.entities.items;

public class Weapon extends Item {
    private final int damage;

    public Weapon(String type, int damage) {
        super(type, 1);
        this.damage = damage;
    }

    @Override
    public String getType() {
        return super.getType();
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
        return "Type: " + getType() + ", Damage: " + damage;
    }
}
