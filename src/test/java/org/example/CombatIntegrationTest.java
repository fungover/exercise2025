package org.example;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.entities.enemies.Goblin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CombatIntegrationTest {

    private Player player;
    private Goblin goblin;

    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer", 100, new Position(1, 1));
        goblin = new Goblin(new Position(1, 1));
    }

    // TEST 1: Testing that when the player attacks the goblin, the goblin's health is reduced by the player's damage amount.
    @Test
    void testPlayerAttackReducesEnemyHealth() {

        int originalHealth = goblin.getHealth(); // Store original health of the enemy
        int playerDamage = player.getDamage(); // Store player's damage value

        player.attack(goblin); // Player attacks the goblin

        assertEquals(originalHealth - playerDamage, goblin.getHealth()); // Enemy health should be reduced by player's damage
    }
}
