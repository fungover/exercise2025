package org.example.entities.items;

import org.example.entities.Item;
import org.example.entities.behaviors.DamageEffect;

public class Weapon extends Item {
    public Weapon(String name, int damage) {
        super(name, new DamageEffect(damage));
    }

    @Override
    public ItemType getType() {
        return ItemType.WEAPON;
    }
}
