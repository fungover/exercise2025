package org.example.service.loot;

import java.util.Optional;
import org.example.entities.items.Item;

/** Produces random loot items. Map/code using this doesn't know specific classes. */
public interface LootFactory {
    /** One roll of the loot table. Empty = no drop. */
    Optional<Item> roll();
}
