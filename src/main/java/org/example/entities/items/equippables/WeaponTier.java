package org.example.entities.items.equippables;

/** Central place for weapon balance values. */
public enum WeaponTier {
    RUSTY(1),
    COMMON(2),
    RARE(3),
    EPIC(4);

    private final int bonusDamage;

    WeaponTier(int bonusDamage) {
        this.bonusDamage = bonusDamage;
    }

    public int bonusDamage() {
        return bonusDamage;
    }
}
