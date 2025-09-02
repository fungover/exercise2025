package org.example.entities.items.consumables;

import org.example.entities.characters.Player;

/** Restores HP based on its PotionTier's healAmount. */
public final class HealthPotion extends Potion {
    private final PotionTier tier;

    /** Default, tier MEDIUM. */
    public HealthPotion() {
        this(PotionTier.MEDIUM);
    }

    public HealthPotion(PotionTier tier) {
        super("Health Potion", "Heals " + tier.healAmount() + " HP");
        this.tier = tier;
    }

    public PotionTier tier() {
        return tier;
    }

    @Override
    public void consume(Player player) {
        int heal = tier.healAmount();
        player.heal(heal);
        System.out.println("You drink a health potion and recover " + heal + " HP.");
    }
}
