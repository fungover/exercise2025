package org.example.service;

import org.example.entities.Player;
import org.example.entities.Enemy;
import org.example.entities.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CombatServiceTest {
    private Player player;
    private Enemy enemy;

    @BeforeEach
    void setUp() {
        player = new Player(0, 0);
        enemy = new Enemy("Test Monster", 50, 10);
    }

    @Test
    void testCalculateDamage() {
        int damage = CombatService.calculateDamage(player);        assertTrue(damage >= 8 && damage <= 12);
    }

    @Test
    void testCalculateDamageWithWeapon() {
        Item sword = new Item("Test Sword", "weapon", 10, "Test weapon");
        player.addItem(sword);

        int damage = CombatService.calculateDamage(player);
        assertTrue(damage >= 16 && damage <= 24);
    }

    @Test
    void testExecuteCombatRound() {
        String result = CombatService.executeCombatRound(player, enemy);
        assertNotNull(result);
        assertTrue(result.contains("You attack"));
        assertTrue(result.contains("damage"));
    }

    @Test
    void testEnemyDefeat() {
        // Set enemy to 1 health to defeat
        enemy.takeDamage(49); // 1 health remaining, player will definitely defeat it

        String result = CombatService.executeCombatRound(player, enemy);
        assertTrue(result.contains("winn")); 
        assertFalse(enemy.isAlive());
    }    @Test
    void testUsePotionWhenHealthy() {
        Item potion = new Item("Health Potion", "potion", 30, "Healing potion");
        player.addItem(potion);

        String result = CombatService.usePotion(player);
        assertTrue(result.contains("already at full health"));
    }

    @Test
    void testUsePotionWhenInjured() {
        Item potion = new Item("Health Potion", "potion", 30, "Healing potion");
        player.addItem(potion);
        player.takeDamage(40); // Player at 60 health

        String result = CombatService.usePotion(player);
        assertTrue(result.contains("restored"));
        assertEquals(90, player.getHealth());
        assertFalse(player.hasPotion()); // Potion should be consumed
    }
}