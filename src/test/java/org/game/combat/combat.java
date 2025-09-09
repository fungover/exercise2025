package org.game.combat;

import org.game.entities.Item;
import org.game.entities.Player;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CombatCalculationsTest {

    @Test
    void takeDamageNoArmor() {
        Player player = new Player("Hero", 1, 1);
        int startHp = player.getHealth();

        // Take damage ignoring armor
        player.takeDamage(10, true);
        int hpAfterTrue = player.getHealth();
        int dealtIgnoringArmor = startHp - hpAfterTrue;

        // Ensure damage registers as positive
        assertThat(dealtIgnoringArmor).isGreaterThan(0);
    }

    @Test
    void takeLessDamageWithArmor() {
        Player player = new Player("Hero", 1, 1);

        int hp0 = player.getHealth();
        player.takeDamage(10, true);
        int hp1 = player.getHealth();
        int dealtIgnoring = hp0 - hp1;

        // Reset by healing via a consumable so next comparison is fair
        Item potion = new Item("Healing Potion", "consumable", 1, 3, true, 10);
        player.addItem(potion);
        player.useItem("Healing Potion");

        int hpBeforeMitigated = player.getHealth();
        player.takeDamage(10, false);
        int hpAfterMitigated = player.getHealth();
        int dealtMitigated = hpBeforeMitigated - hpAfterMitigated;

        // When armor is considered damage should be less than or equal to the raw damage
        assertThat(dealtMitigated).isGreaterThanOrEqualTo(0);
        assertThat(dealtMitigated).isLessThanOrEqualTo(dealtIgnoring);
    }

    @Test
    void playerCanDie() {
        Player player = new Player("Hero", 1, 1);
        // Deal large damage ignoring armor to ensure death
        player.takeDamage(10_000, true);
        assertThat(player.isAlive()).isFalse();
        assertThat(player.getHealth()).isLessThanOrEqualTo(0);
    }
}