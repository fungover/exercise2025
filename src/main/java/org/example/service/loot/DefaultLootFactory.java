// src/main/java/org/example/service/loot/DefaultLootFactory.java
package org.example.service.loot;

import java.util.Optional;
import java.util.Random;
import org.example.entities.items.Item;
import org.example.entities.items.consumables.*;
import org.example.entities.items.equippables.*;

/** Simple, seedable loot table with readable weights. */
public final class DefaultLootFactory implements LootFactory {
    private final Random rng;

    public DefaultLootFactory(long seed) {
        this.rng = new Random(seed ^ 0xC0FFEE1234ABCDL);
    }

    @Override
    public Optional<Item> roll() {
        int roll = rng.nextInt(100); // 0..99

        if (roll < 35) { // 35% health potion
            return Optional.of(new HealthPotion(randomPotionTier()));
        } else if (roll < 50) { // +15% damage potion
            return Optional.of(new DamagePotion(randomPotionTier()));
        } else if (roll < 75) { // +25% weapon
            return Optional.of(rng.nextBoolean()
                    ? new Sword(WeaponTier.COMMON)
                    : new Axe(WeaponTier.COMMON));
        } else { // 25% armor
            return Optional.of(rng.nextBoolean()
                    ? new LeatherArmor(ArmorTier.LIGHT)
                    : new Chainmail(ArmorTier.MEDIUM));
        }
    }

    private PotionTier randomPotionTier() {
        PotionTier[] tiers = PotionTier.values();
        return tiers[rng.nextInt(tiers.length)];
    }
}
