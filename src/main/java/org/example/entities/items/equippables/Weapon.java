package org.example.entities.items.equippables;

import org.example.entities.characters.Player;
import org.example.entities.items.Equippable;
import org.example.entities.items.Inventory;
import org.example.entities.items.Item;

public abstract class Weapon extends Item implements Equippable {
    private final int bonusDamage;

    protected Weapon(String name, String description, int bonusDamage) {
        super(name, description);
        this.bonusDamage = bonusDamage;
    }

    /** Flat damage added to the player's base damage. */
    public int bonusDamage() {
        return bonusDamage;
    }

    @Override public Slot slot() {
        return Slot.WEAPON;
    }

    @Override
    public boolean onUse(Player player, Inventory inventory) {
        inventory.equip(this, player); // keep item in backpack; just equip
        return true;
    }
}
