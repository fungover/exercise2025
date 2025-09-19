package org.example.service.combat;

import org.example.entities.characters.Warrior;
import org.example.entities.enemies.Goblin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class CombatServiceTest {

    @Test
    void attack_once_applies_damage_to_both_sides_when_enemy_survives() {
        final CombatService combatService = new CombatService(); // flee RNG unused here
        final Warrior player = new Warrior("Hero", 0, 0); // baseDamage known by class
        final Goblin enemy = new Goblin();

        final int initialEnemyHp = enemy.health();
        final int initialPlayerHp = player.getHealth();
        final int expectedPlayerDamage = player.attackDamage();

        assertTrue(expectedPlayerDamage < initialEnemyHp,
                "Enemy must survive the player's hit for this test.");

        final CombatService.RoundResult roundResult =
                combatService.attackOnce(player, enemy, player.getInventory());

        // Player’s dealt damage equals attackDamage
        assertEquals(expectedPlayerDamage, roundResult.playerDamageDealt(),
                "Player damage should equal attackDamage()");

        // Enemy should strike back if alive after player's hit
        assertEquals(enemy.attackDamage(), roundResult.enemyDamageDealt(),
                "Enemy should strike back if still alive");

        // HP bookkeeping should match the round result
        assertEquals(initialEnemyHp  - roundResult.playerDamageDealt(), enemy.health(),
                "Enemy HP should go down by the damage dealt");
        assertEquals(initialPlayerHp - roundResult.enemyDamageDealt(),  player.getHealth(),
                "Player HP should go down by the enemy damage");

        assertFalse(roundResult.enemyDead(),"Enemy should still be alive after one base hit");
        assertFalse(roundResult.playerDead(),"Player should still be alive");
    }

    @Test
    void next_attack_bonus_is_applied_once_then_consumed() {
        final CombatService combatService = new CombatService();
        final Warrior player = new Warrior("Hero", 0, 0);
        final Goblin enemy = new Goblin();

        final int baseDamage = player.attackDamage();
        player.getInventory().grantNextAttackBonus(5);

        final CombatService.RoundResult firstRound =
                combatService.attackOnce(player, enemy, player.getInventory());
        assertEquals(baseDamage + 5, firstRound.playerDamageDealt(),
                "First hit should include the one-off bonus");

        // Even if enemy died on the first blow, the bonus must already be consumed
        final CombatService.RoundResult secondRound =
                combatService.attackOnce(player, enemy, player.getInventory());
        assertEquals(baseDamage, secondRound.playerDamageDealt(),
                "Second hit should be back to base damage (bonus consumed)");
    }

    @Test
    void enemy_eventually_dies_after_enough_rounds() {
        final CombatService combatService = new CombatService();
        final Warrior player = new Warrior("Hero", 0, 0);
        final Goblin enemy = new Goblin();

        while (!enemy.isDead()) {
            combatService.attackOnce(player, enemy, player.getInventory());
        }
        assertTrue(enemy.isDead(), "Enemy should be dead after repeated rounds");
    }

    @Test
    void enemy_killed_by_first_hit_does_not_strike_back() {
        final CombatService combatService = new CombatService();
        final Warrior player = new Warrior("Hero", 0, 0);
        final Goblin enemy = new Goblin();

        final int playerHpBefore = player.getHealth();

        // Ensure the first blow kills the enemy (big one-off bonus)
        player.getInventory().grantNextAttackBonus(100);

        final CombatService.RoundResult round =
                combatService.attackOnce(player, enemy, player.getInventory());

        assertTrue(round.enemyDead(), "Enemy should die from the huge hit");
        assertEquals(0, round.enemyDamageDealt(), "Dead enemy must not strike back");
        assertEquals(playerHpBefore, player.getHealth(),
                "Player HP should be unchanged if enemy died before retaliation");
    }

    @Test
    void player_bonus_does_not_increase_enemy_damage() {
        final CombatService combatService = new CombatService();
        final Warrior player = new Warrior("Hero", 0, 0);
        final Goblin enemy = new Goblin();

        // Grant a bonus to the PLAYER only
        player.getInventory().grantNextAttackBonus(5);

        final int expectedEnemyDamage = enemy.attackDamage();
        final int playerHpBefore = player.getHealth();

        final CombatService.RoundResult round =
                combatService.attackOnce(player, enemy, player.getInventory());

        if (!round.enemyDead()) { // Only check if enemy survived to strike back
            assertEquals(expectedEnemyDamage, round.enemyDamageDealt(),
                    "Player's bonus must not affect enemy damage");
            assertEquals(playerHpBefore - expectedEnemyDamage, player.getHealth(),
                    "Player HP should drop exactly by enemy's base damage if enemy survives");
        }
    }
}
