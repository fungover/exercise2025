package org.SpinalGlitter.exercise2.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {


    @Test
    void consumeFirstPotion() {
        Player player = new Player("Hero");
        player.takeDamage(20);

        assertEquals(80, player.getCurrentHealth(), "Player health should be 80 after taking 20 damage");

        player.getInventory().addItem(new Potion(null));
        player.heal(20);

        assertTrue(player.getInventory().getItems().isEmpty(), "Inventory should be empty after using the only potion");
        assertEquals(100, player.getCurrentHealth(), "Player health should be 100 after healing with potion");

    }

    @Test
    void playerDamageWithAndWithoutSword() {

        Player player = new Player("Hero");
        player.getInventory().addItem(new Sword(null));
        player.setDamage();

        assertEquals(20, player.getDamage(), "Damage should be 20 with sword");

        player.getInventory().removeWeapon();
        player.setDamage();

        assertEquals(10, player.getDamage(), "Damage should be 10 after removing sword");

    }
}