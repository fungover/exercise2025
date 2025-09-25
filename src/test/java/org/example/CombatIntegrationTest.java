package org.example;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.entities.enemies.Goblin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    // TEST 2: Testing that when the goblin attacks the player, the player's health is reduced by the goblin's damage amount minus player's defense (armor).
    @Test
    void testEnemyAttackWithArmor() {

        player.setDefense(3); // Set player's defense to 3 (-3 damage reduction)
        int originalHealth = player.getHealth(); // Store original health of the player (100)
        int goblinDamage = goblin.getDamage(); // Store goblin's damage value (8)
        int expectedDamage = Math.max(1, goblinDamage - player.getDefense()); // Calculate expected damage after armor reduction (should be 5)

        goblin.attack(player); // Goblin attacks the player

        assertEquals(originalHealth - expectedDamage, player.getHealth()); // Player health should be reduced by expected damage (100 - 5 = 95)
    }

    // TEST 3: Testing that when the goblin attacks a player with low health, the player's health reaches zero and the player is marked as dead.
    @Test
    void testPlayerDeath() {
        player.takeDamage(95, 95); // Reduce player health to 5
        assertFalse(player.isDead());

        goblin.attack(player);

        assertTrue(player.isDead());
        assertEquals(0, player.getHealth());
    }
}
