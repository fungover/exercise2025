package org.example.entities.items.equippables;

import static org.example.utils.Guard.notNull;

public final class LeatherArmor extends Armor {
    private final ArmorTier tier;

    /** Default leather, tier (LIGHT). */
    public LeatherArmor() {
        this(ArmorTier.LIGHT);
    }

    public LeatherArmor(ArmorTier tier) {
        super("Leather Armor",
                "Leather Armor (-" + notNull(tier, "tier").damageReduction() + " dmg taken)",
                tier.damageReduction());
        this.tier = tier;
    }

    public ArmorTier tier() {
        return tier;
    }
}
