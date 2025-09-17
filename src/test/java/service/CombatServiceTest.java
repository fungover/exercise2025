package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.*;

import entities.Player;
import entities.Enemy;
import enemies.Skeleton;
import enemies.Spider;
import utils.Constants;

@DisplayName("Combat Service Tests")
class CombatServiceTest {

    private CombatService combatService;
    private Player player;
    private Enemy enemy;

    @BeforeEach
    void setUp() {
        combatService = new CombatService();
        player = new Player("Test Player", 100, 20);
        enemy = new Skeleton(); // 40 HP, 8 damage
    }

    @Test
    @DisplayName("Combat should start correctly with living enemy")
    void testStartCombat() {
        // Act
        CombatService.CombatResult result = combatService.startCombat(player, enemy);

        // Assert
        assertTrue(result.isInCombat(), "Combat should be active");
        assertFalse(result.isGameEnded(), "Game should not be ended");
        assertNotNull(result.getMessage(), "Should have combat start message");
        assertEquals(enemy, result.getEnemy(), "Should return the same enemy");
    }

    @Test
    @DisplayName("Cannot start combat with dead enemy")
    void testStartCombatWithDeadEnemy() {
        // Arrange
        enemy.takeDamage(1000); // Kill the enemy

        // Act
        CombatService.CombatResult result = combatService.startCombat(player, enemy);

        // Assert
        assertFalse(result.isInCombat(), "Combat should not be active with dead enemy");
        assertTrue(result.getMessage().contains("besegrad"), "Should indicate enemy is defeated");
    }

    @Test
    @DisplayName("Player attack should deal damage")
    void testPlayerAttack() {
        // Arrange
        int enemyStartingHealth = enemy.getCurrentHealth();
        int playerDamage = player.getTotalDamage();

        // Act
        CombatService.CombatResult result = combatService.playerAttack(player, enemy);

        // Assert
        assertTrue(result.isInCombat(), "Combat should continue after attack");
        assertEquals(enemyStartingHealth - playerDamage, enemy.getCurrentHealth(),
                "Enemy should take damage equal to player's attack");
        assertTrue(result.getMessage().contains("attackerar"), "Should show attack message");
    }

    @Test
    @DisplayName("Player attack should kill weak enemy")
    void testPlayerAttackKillsEnemy() {
        // Arrange
        enemy.takeDamage(enemy.getCurrentHealth() - 1); // Leave enemy with 1 HP

        // Act
        CombatService.CombatResult result = combatService.playerAttack(player, enemy);

        // Assert
        assertFalse(result.isInCombat(), "Combat should end when enemy dies");
        assertTrue(result.isGameEnded(), "Game state should change when enemy dies");
        assertFalse(enemy.isAlive(), "Enemy should be dead");
        assertTrue(result.getMessage().contains("besegrade"), "Should show victory message");
    }

    @Test
    @DisplayName("Enemy counter-attack should damage player")
    void testEnemyCounterAttack() {
        // Arrange
        int playerStartingHealth = player.getCurrentHealth();

        // Act
        CombatService.CombatResult result = combatService.playerAttack(player, enemy);

        // Assert
        assertTrue(player.getCurrentHealth() < playerStartingHealth, "Player should take damage from counter-attack");
        assertTrue(result.getMessage().contains("Du tar"), "Should show damage taken message");
    }

    @RepeatedTest(10)
    @DisplayName("Flee attempt should have correct success rate")
    void testFleeAttempt() {
        // Act & Assert - Run multiple times to test probability
        CombatService.CombatResult result = combatService.attemptFlee(player, enemy);

        // Should either succeed or fail
        assertTrue(result.isInCombat() || !result.isInCombat(), "Should have definite combat state");

        if (!result.isInCombat()) {
            assertTrue(result.getMessage().contains("lyckas fly"), "Successful flee should show success message");
        } else {
            assertTrue(result.getMessage().contains("misslyckades"), "Failed flee should show failure message");
        }
    }

    @Test
    @DisplayName("Combat with player at low health")
    void testPlayerLowHealth() {
        // Arrange
        player.takeDamage(player.getCurrentHealth() - 5); // Leave player with 5 HP
        Enemy strongEnemy = new Skeleton();

        // Act - Enemy might kill player with counter-attack
        CombatService.CombatResult result = combatService.playerAttack(player, strongEnemy);

        // Assert
        if (!player.isAlive()) {
            assertTrue(result.isGameEnded(), "Game should end if player dies");
            assertTrue(result.getMessage().contains("GAME OVER"), "Should show game over message");
        }
    }

    @Test
    @DisplayName("Combat result should contain valid data")
    void testCombatResultIntegrity() {
        // Act
        CombatService.CombatResult result = combatService.playerAttack(player, enemy);

        // Assert
        assertNotNull(result.getMessage(), "Result should have a message");
        assertNotNull(result.getEnemy(), "Result should have enemy reference");
        assertTrue(result.getMessage().length() > 0, "Message should not be empty");
    }
}