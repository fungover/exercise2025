package org.example.entities.items.equippables;

import org.example.entities.characters.Player;
import org.example.entities.items.Equippable;
import org.example.entities.items.Inventory;
import org.example.entities.items.Item;

public abstract class Armor extends Item implements Equippable {
    private final int damageReduction;

    protected Armor(String name, String description, int damageReduction) {
        super(name, description);
        this.damageReduction = damageReduction;
    }

    /** Flat damage reduction applied when the player takes damage. */
    public int damageReduction() {
        return damageReduction;
    }

    @Override public Slot slot() {
        return Slot.ARMOR;
    }

    @Override
    public boolean onUse(Player player, Inventory inventory) {
        inventory.equip(this, player);
        return true;
    }
}
