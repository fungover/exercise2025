package org.example.entities.items;

import org.example.entities.characters.Player;

public interface Consumable {
    /** Apply the effect, then the item will be removed from the inventory. */
    void consume(Player player);
}
