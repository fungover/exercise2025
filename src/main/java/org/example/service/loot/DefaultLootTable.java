package org.example.service.loot;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import org.example.entities.items.Item;
import org.example.entities.items.consumables.DamagePotion;
import org.example.entities.items.consumables.HealthPotion;
import org.example.entities.items.consumables.PotionTier;
import org.example.entities.items.equippables.*;
import org.example.utils.WeightedPicker;

public final class DefaultLootTable implements LootTable {
    private Random random;

    // Top-level category of drop
    private enum Category { NOTHING, HEALTH_POTION, DAMAGE_POTION, WEAPON, ARMOR }

    // Adjust these weights to balance the game
    private final WeightedPicker<Category> categoryPicker =
            new WeightedPicker.Builder<Category>()
                    .add(Category.NOTHING,25)  // 25%
                    .add(Category.HEALTH_POTION,30)  // 30%
                    .add(Category.DAMAGE_POTION,15)  // 15%
                    .add(Category.WEAPON,15)  // 15%
                    .add(Category.ARMOR,15)  // 15%
                    .build();

    // Potion tier odds
    private final WeightedPicker<PotionTier> potionTierPicker =
            new WeightedPicker.Builder<PotionTier>()
                    .add(PotionTier.SMALL,1)
                    .add(PotionTier.MEDIUM,1)
                    .add(PotionTier.LARGE,1)
                    .build();

    // Weapon tier odds
    private final WeightedPicker<WeaponTier> weaponTierPicker =
            new WeightedPicker.Builder<WeaponTier>()
                    .add(WeaponTier.COMMON,70)
                    .add(WeaponTier.RARE,25)
                    .add(WeaponTier.EPIC,5)
                    .build();

    // Armor tier odds
    private final WeightedPicker<ArmorTier> armorTierPicker =
            new WeightedPicker.Builder<ArmorTier>()
                    .add(ArmorTier.LIGHT,70)
                    .add(ArmorTier.MEDIUM,25)
                    .add(ArmorTier.HEAVY,5)
                    .build();

    // Weapon identities
    private final WeightedPicker<Supplier<Item>> weaponIdentityPicker =
            new WeightedPicker.Builder<Supplier<Item>>()
                    .add(() -> new Sword(weaponTierPicker.pick(random)),60)
                    .add(() -> new Axe(weaponTierPicker.pick(random)),40)
                    .build();

    // Armor identities
    private final WeightedPicker<Supplier<Item>> armorIdentityPicker =
            new WeightedPicker.Builder<Supplier<Item>>()
                    .add(() -> new LeatherArmor(armorTierPicker.pick(random)),55)
                    .add(() -> new Chainmail(armorTierPicker.pick(random)),45)
                    .build();

    public DefaultLootTable(long seed) {
        this.random = new Random(seed ^ 0xC0FFEE1234ABCDL);
    }

    @Override
    public Optional<Item> roll() {
        Category chosen = categoryPicker.pick(random);
        return switch (chosen) {
            case NOTHING -> Optional.empty();
            case HEALTH_POTION -> Optional.of(new HealthPotion(potionTierPicker.pick(random)));
            case DAMAGE_POTION -> Optional.of(new DamagePotion(potionTierPicker.pick(random)));
            case WEAPON -> Optional.of(weaponIdentityPicker.pick(random).get());
            case ARMOR -> Optional.of(armorIdentityPicker.pick(random).get());
        };
    }
}
