package org.example.entities.items.consumables;

/** Shared tiers for all potions, each tier defines: how much healing AND how much damage bonus it grants. */
public enum PotionTier {
    SMALL(15, 1),
    MEDIUM(25, 2),
    LARGE(50, 3);

    private final int healAmount;
    private final int nextAttackBonus;

    PotionTier(int healAmount, int nextAttackBonus) {
        this.healAmount = healAmount;
        this.nextAttackBonus = nextAttackBonus;
    }

    /** HP restored by a HealthPotion of this tier. */
    public int healAmount() {
        return healAmount;
    }

    /** One-time bonus damage granted by a DamagePotion of this tier. */
    public int nextAttackBonus() {
        return nextAttackBonus;
    }
}
