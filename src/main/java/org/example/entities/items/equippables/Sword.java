package org.example.entities.items.equippables;

public final class Sword extends Weapon {
    private final WeaponTier tier;

    /** Default sword, tier (COMMON). */
    public Sword() {
        this(WeaponTier.COMMON);
    }

    public Sword(WeaponTier tier) {
        super("Sword",
                "Sword (+" + tier.bonusDamage() + " damage)",
                tier.bonusDamage());
        this.tier = tier;
    }

    public WeaponTier tier() {
        return tier;
    }
}
