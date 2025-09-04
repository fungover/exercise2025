package org.example.entities;

public class Weapon extends Item {
    protected int damageBonus;

    public Weapon(String name, String description, int damageBonus) {
        super(name, description, ItemType.Weapon);
        this.damageBonus = damageBonus;
    }

    public int getDamageBonus() {return damageBonus;}

    @Override public void use(Player player) {
        System.out.println("Weapons are automatically equipped when picked up!");
    }
}
