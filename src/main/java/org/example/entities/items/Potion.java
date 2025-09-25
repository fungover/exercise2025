package org.example.entities.items;

import org.example.entities.Item;
import org.example.entities.behaviors.HealingEffect;

public class Potion extends Item {
    public Potion(String name, int healthRestored) {
        super(name, new HealingEffect(healthRestored));
    }

    @Override
    public ItemType getType() {
        return ItemType.POTION;
    }
}
