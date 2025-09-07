package org.example.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DamageTest {

    @Test
    void testPlayerBaseAttackDamage() {
        Player player = new Player("TestPlayer");
        // Player Base attack damage = 5
        assertEquals(5, player.attack());
    }

    @Test
    void testPlayerBaseAttackAndWeaponDamageTogether() {
        Player player = new Player("TestPlayer");
        Weapon sword = new Weapon("Sword", "Test sword", "Weapon", 1, 10);
        player.addItem(sword);
        player.equipWeapon("Sword");

        // Player Base attack damage = 5, Weapon damage = 10. 5 + 10 = 15
        assertEquals(15, player.attack());
    }

    @Test
    void testEnemyTakeDamageAndCheckHealth() {
        Enemy enemy = new Enemy("TestEnemy", "Testing creepy enemy", 30, 10);
        // Enemy 30 health and alive
        assertEquals(30, enemy.getHealth());
        assertTrue(enemy.isAlive());
        // Enemy takes 15 damage
        enemy.takeDamage(15);
        // Enemy should have 15 health left and still be alive
        assertEquals(15, enemy.getHealth());
        assertTrue(enemy.isAlive());
        // Enemy takes 15 damage again and should not be alive anymore
        enemy.takeDamage(15);
        assertEquals(0, enemy.getHealth());
        assertFalse(enemy.isAlive());
    }
}
