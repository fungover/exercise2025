package org.example;

import entities.HealingPotion;
import entities.Player;
import org.junit.jupiter.api.Test;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

class ItemEffectsTest {

    @Test
    void healingPotion_healsAndIsConsumed() {
        Player player = new Player();
        player.setPosition(new Position(0,0));
        player.takeDamage(30);
        int hpAfterDamage = player.getHealth();

        HealingPotion potion = new HealingPotion("Small Potion", "potion", 20);
        player.addItem(potion);
        assertTrue(player.getInventory().contains(potion));

        player.useItem(potion);

        assertEquals(hpAfterDamage + 20, player.getHealth());
        assertFalse(player.getInventory().contains(potion));
    }
}