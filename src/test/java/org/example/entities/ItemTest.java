package org.example.entities;

import org.example.game.GameLoop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test void drinkHealingPotion() {
        GameLoop gameLoop = new GameLoop();
        MockPlayer mockPlayer = new MockPlayer();
        HealthPotion healthPotion = new HealthPotion();

        mockPlayer.addItem(healthPotion);
        mockPlayer.setHealth(75);

        int healthBefore = mockPlayer.getHealth();

        gameLoop.setPlayer(mockPlayer);
        gameLoop.useItem("1");

        int healthAfter = mockPlayer.getHealth();

        assertEquals(100, mockPlayer.getHealth());
        assertTrue(healthAfter > healthBefore);
        assertFalse(healthAfter == healthBefore);
        assertFalse(mockPlayer.getInventory().contains(healthPotion));
    }

    @Test void weaponDamageIncrease() {
        GameLoop gameLoop = new GameLoop();
        MockPlayer mockPlayer = new MockPlayer();
        gameLoop.setPlayer(mockPlayer);

        Sword sword = new Sword();
        int damgeBefore = mockPlayer.getDamage();
        mockPlayer.addItem(sword);
        int damgeAfter = mockPlayer.getDamage();

        assertEquals(15, damgeAfter);

    }

}