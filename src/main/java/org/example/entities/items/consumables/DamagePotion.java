package org.example.entities.items.consumables;

import org.example.entities.characters.Player;

/** Grants a one-time bonus to the player's next attack, the bonus is stored in Inventory; combat applies & clears it. */
public final class DamagePotion extends Potion {
    private final PotionTier tier;

    /** Default = MEDIUM. */
    public DamagePotion() {
        this(PotionTier.MEDIUM);
    }

    public DamagePotion(PotionTier tier) {
        super("Damage Potion", "Next attack +" + tier.nextAttackBonus() + " damage");
        this.tier = tier;
    }

    public PotionTier tier() {
        return tier;
    }

    @Override
    public void consume(Player player) {
        int bonus = tier.nextAttackBonus();
        player.getInventory().grantNextAttackBonus(bonus);
        System.out.println("You quaff a damage potion! Your next attack gains +" + bonus + " damage.");
    }
}
