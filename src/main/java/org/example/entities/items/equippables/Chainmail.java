package org.example.entities.items.equippables;

import static org.example.utils.Guard.notNull;

public final class Chainmail extends Armor {
    private final ArmorTier tier;

    /** Default chainmail, tier (MEDIUM). */
    public Chainmail() {
        this(ArmorTier.MEDIUM);
    }

    public Chainmail(ArmorTier tier) {
        super("Chainmail",
                "Chainmail (-" + notNull(tier, "tier").damageReduction() + " dmg taken)",
                tier.damageReduction());
        this.tier = tier;
    }

    public ArmorTier tier() {
        return tier;
    }
}
