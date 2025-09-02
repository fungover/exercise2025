package org.example.entities.items.consumables;

import org.example.entities.items.Consumable;
import org.example.entities.items.Inventory;
import org.example.entities.items.Item;
import org.example.entities.characters.Player;

/** Base class for all potions. Concrete subclasses implement the actual effect. */
public abstract class Potion extends Item implements Consumable {

    protected Potion(String name, String description) {
        super(name, description);
    }

    /** All potions must apply their effect and print a message. */
    @Override
    public abstract void consume(Player player);

    public boolean onUse(Player player, Inventory inventory) {
        consume(player);         // do the effect
        inventory.remove(this);  // potions are consumed
        return true;
    }
}
