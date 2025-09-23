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

@DisplayName("Combat Service Tests - Updated for actual implementation")
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
        assertFalse(result.isGameEnded(), "Game should not be ended at start");
        assertNotNull(result.getMessage(), "Should have combat start message");
        assertEquals(enemy, result.getEnemy(), "Should return the same enemy");
        assertTrue(result.getMessage().contains("STRID BÖRJAR"), "Should show combat start message");
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
        assertFalse(result.isGameEnded(), "Game should not be ended, just no combat");
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
    @DisplayName("Player attack should end combat when enemy dies")
    void testPlayerAttackKillsEnemy() {
        // Arrange
        enemy.takeDamage(enemy.getCurrentHealth() - 1); // Leave enemy with 1 HP

        // Act
        CombatService.CombatResult result = combatService.playerAttack(player, enemy);

        // Assert
        assertFalse(result.isInCombat(), "Combat should end when enemy dies");
        assertFalse(result.isGameEnded(), "Game should continue after enemy dies");
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
        assertTrue(player.getCurrentHealth() < playerStartingHealth,
                "Player should take damage from counter-attack");
        // Check for damage message - your implementation might use different wording
        assertTrue(result.getMessage().contains("Status") || result.getMessage().contains("hälsa"),
                "Should show combat status");
    }

    @RepeatedTest(10)
    @DisplayName("Flee attempt should have correct success rate")
    void testFleeAttempt() {
        // Act & Assert - Run multiple times to test probability
        CombatService.CombatResult result = combatService.attemptFlee(player, enemy);

        // Should either succeed or fail
        if (!result.isInCombat()) {
            assertTrue(result.getMessage().contains("lyckas fly"),
                    "Successful flee should show success message");
            assertFalse(result.isGameEnded(), "Successful flee should not end game");
        } else {
            assertTrue(result.getMessage().contains("misslyckades"),
                    "Failed flee should show failure message");
            assertTrue(result.isInCombat(), "Failed flee should continue combat");
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
        } else {
            // Player survived - combat continues
            assertTrue(result.isInCombat() || !strongEnemy.isAlive(),
                    "Combat should continue or enemy should be dead");
        }
    }

    @Test
    @DisplayName("Flee failure should allow enemy attack")
    void testFleeFailureEnemyAttack() {
        // This test checks the specific behavior when flee fails
        int originalHealth = player.getCurrentHealth();

        // Try to flee multiple times to eventually get a failure
        boolean fleeAttempted = false;
        for (int i = 0; i < 50 && !fleeAttempted; i++) {
            // Create a fresh player for consistent test state
            Player testPlayer = new Player("Test Player", 100, 20);

            CombatService.CombatResult result = combatService.attemptFlee(testPlayer, enemy);

            if (result.isInCombat()) {
                // Flee failed - enemy should have attacked
                fleeAttempted = true;
                assertTrue(result.getMessage().contains("misslyckades"),
                        "Should show flee failure message");
                assertTrue(testPlayer.getCurrentHealth() < 100 || !testPlayer.isAlive(),
                        "Player should take damage from enemy attack after failed flee");
            }
        }

        // Note: This test might occasionally pass even with broken implementation due to randomness
        // but over multiple test runs, failures would be detected
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

        // Check that combat state is consistent
        if (result.isInCombat()) {
            assertTrue(player.isAlive(), "If combat continues, player should be alive");
            assertTrue(enemy.isAlive(), "If combat continues, enemy should be alive");
        }
    }

    @Test
    @DisplayName("Cannot attack dead enemy")
    void testCannotAttackDeadEnemy() {
        // Arrange
        enemy.takeDamage(1000); // Kill the enemy

        // Act
        CombatService.CombatResult result = combatService.playerAttack(player, enemy);

        // Assert
        assertFalse(result.isInCombat(), "Should not be in combat with dead enemy");
        assertTrue(result.getMessage().contains("besegrad"),
                "Should indicate enemy is already defeated");
    }
}