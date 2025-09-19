package org.example.entities.items.equippables;

/** Central place for armor balance values. */
public enum ArmorTier {
    LIGHT(1),
    MEDIUM(2),
    HEAVY(3);

    private final int damageReduction;

    ArmorTier(int damageReduction) {
        this.damageReduction = damageReduction;
    }

    public int damageReduction() {
        return damageReduction;
    }
}
