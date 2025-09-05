package org.example.service;

import org.example.entities.characters.Player;
import org.example.entities.enemies.Enemy;
import org.example.entities.enemies.Goblin;
import org.example.entities.items.Potion;
import org.example.entities.items.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CombatServiceTest {
    private CombatService combatService;
    private Player player;
    private Enemy enemy;
    private Weapon testWeapon;
    private InputService mockInputService;

    @BeforeEach
    void setUp() {
        combatService = new CombatService();
        testWeapon = new Weapon("Test Sword", "A test weapon", 10, 15);
        player = new Player("TestPlayer", 50, 100, 2, 2, testWeapon);
        enemy = new Goblin();

        mockInputService = mock(InputService.class);
    }

    @Test
    @DisplayName("Player attacks enemy (Combat calculations)")
    void testPlayerDealsDamage() {
        when(mockInputService.readLine()).thenReturn("1");
        int initialEnemyHP = enemy.getHealth();

        combatService.battleEnemy(player, enemy, mockInputService);

        assertTrue(enemy.getHealth() < initialEnemyHP);
    }

    @Test
    @DisplayName("Player uses health potion (Item effects)")
    void testPlayerUsesHealthPotion() {
        when(mockInputService.readLine()).thenReturn("2", "1", "1");
        player.getInventory().addItem(new Potion("Health Potion", "Restores health.", 20, 40));
        int initialPlayerHP = player.getHealth();

        combatService.battleEnemy(player, enemy, mockInputService);

        assertTrue(player.getHealth() > initialPlayerHP);
    }

    @Test
    @DisplayName("Enemy attacks player (Combat calculations)")
    void testEnemyDealsDamage() {
        when(mockInputService.readLine()).thenReturn("1");
        int initialPlayerHP = player.getHealth();

        combatService.battleEnemy(player, enemy, mockInputService);

        assertTrue(player.getHealth() < initialPlayerHP);
    }

    @Test
    @DisplayName("Test that damage dealt and message have the same values")
    void testDamageDealtAndMessageHaveSameValues() {
        Weapon sameDamageWeapon = new Weapon("Same Min & Max Damage Weapon", "Same min and max damage", 10, 10);
        player.setEquippedWeapon(sameDamageWeapon);

        int initialHealth = enemy.getHealth();
        int playerDamage = player.getDamage();

        enemy.takeDamage(playerDamage);

        String attackMessage = player.getAttackMessage(enemy, playerDamage);

        assertThat(enemy.getHealth()).isEqualTo(20);
        assertThat(attackMessage).isEqualTo("> TestPlayer attacks Goblin for 10 damage!");
    }
}
