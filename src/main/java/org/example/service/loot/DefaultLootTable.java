package org.example.service.loot;

import java.util.Optional;
import java.util.Random;
import org.example.entities.items.Item;
import org.example.entities.items.consumables.DamagePotion;
import org.example.entities.items.consumables.HealthPotion;
import org.example.entities.items.consumables.PotionTier;
import org.example.entities.items.equippables.ArmorTier;
import org.example.entities.items.equippables.Chainmail;
import org.example.entities.items.equippables.Sword;
import org.example.entities.items.equippables.WeaponTier;

/** Seedable loot weights; tweak percentages here. */
public final class DefaultLootTable implements LootTable {
    private final Random rng;

    public DefaultLootTable(long seed) {
        this.rng = new Random(seed ^ 0xC0FFEE1234ABCDL);
    }

    @Override public Optional<Item> roll() {
        int roll = rng.nextInt(100); // 0..99

        // 30% nothing
        if (roll < 30) return Optional.empty();

        // 30% health potion
        if (roll < 60) return Optional.of(new HealthPotion(randomPotionTier()));

        // 15% damage potion
        if (roll < 75) return Optional.of(new DamagePotion(randomPotionTier()));

        // 15% weapon (only Sword for now; add Axe later if you add the class)
        if (roll < 90) return Optional.of(new Sword(WeaponTier.COMMON));

        // 10% armor (Chainmail available today)
        return Optional.of(new Chainmail(ArmorTier.MEDIUM));
    }

    private PotionTier randomPotionTier() {
        PotionTier[] tiers = PotionTier.values();
        return tiers[rng.nextInt(tiers.length)];
    }
}
