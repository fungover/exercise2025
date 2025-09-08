package org.example.entities.items;

public class Weapon extends Item {
    private int damage;

    public Weapon(String type, int damage) {
        int quantity = 1;
        super(type, quantity);
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
