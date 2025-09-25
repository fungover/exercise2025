package org.example.entities.items;

import org.example.entities.characters.Player;

public interface Equippable {
    /** Which slot this item uses. I could add more later like, rings, shield etc  */
    enum Slot { WEAPON, ARMOR }

    Slot slot();

    /** Hooks if I later want passive effects on equip/unequip. */
    default void onEquip(Player player) {}
    default void onUnequip(Player player) {}
}
