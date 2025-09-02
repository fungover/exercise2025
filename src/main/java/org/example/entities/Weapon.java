package org.example.entities;

public class Weapon extends Item {
    private int damage;

    public Weapon(String name, String description, String type, int quantity, int damage) {
        super(name, description, type, quantity);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void displayInfo() {
        System.out.println("Weapon: " + getName() + " - " + getDescription() + " - Damage: " + getDamage() +
                " - Quantity: " + getQuantity());
    }

    @Override
    public String toString() {
        return getName() + " (Damage: " + damage + ")";
    }

}
