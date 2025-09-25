package org.example.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealingTest {

    @Test
    void testHealingEffect() {
        Player player = new Player("TestPlayerForHealing");
        // Player start health = 100
        assertEquals(100, player.getHealth());

        // Player takes 50 damage = 50 hp left
        player.takeDamage(50);
        assertEquals(50, player.getHealth());

        // Player heals 30 hp = 80 hp left
        player.heal(30);
        assertEquals(80, player.getHealth());

    }
}
