package entities;

/**
 * HealingPotion återställer en viss mängd HP upp till maxHealth.
 */
public class HealingPotion implements Item {
    private final String name;
    private final int healAmount;

    public HealingPotion(int healAmount) {
        this.name = "Healing Potion";
        this.healAmount = healAmount;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Player player) {
        if (player != null && player.isAlive()) {
            player.heal(healAmount);
        }
    }
}
