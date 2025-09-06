package org.example.entities.items.equippables;

import static org.example.utils.Guard.notNull;

public final class Axe extends Weapon {
    private final WeaponTier tier;

    /** Default axe, tier (COMMON). */
    public Axe() {
        this(WeaponTier.COMMON);
    }

    public Axe(WeaponTier tier) {
        super("Axe",
                "Axe (+" + notNull(tier, "tier").bonusDamage() + " damage)",
                tier.bonusDamage());
        this.tier = tier;
    }

    public WeaponTier tier() {
        return tier;
    }
}
