package org.example.entities.items.equippables;

public final class Axe extends Weapon {
    private final WeaponTier tier;

    /** Default axe, tier (COMMON). */
    public Axe() {
        this(WeaponTier.COMMON);
    }

    public Axe(WeaponTier tier) {
        super("Axe",
                "Axe (+" + tier.bonusDamage() + " damage)",
                tier.bonusDamage());
        this.tier = tier;
    }

    public WeaponTier tier() {
        return tier;
    }
}
