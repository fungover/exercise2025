package org.example.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(1, 0);
    }

    @Test
    void testPlayerInitialization() {
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
        assertEquals(100, player.getHealth());
        assertEquals(10, player.getDamage());
        assertTrue(player.isAlive());
        assertEquals(0, player.getInventorySize());
    }

    @Test
    void testMovement() {
        player.moveUp();
        assertEquals(-1, player.getY()); // Y decreases when moving up

        player.moveDown();
        assertEquals(0, player.getY()); // Y increases when moving down

        player.moveLeft();
        assertEquals(0, player.getX());

        player.moveRight();
        assertEquals(1, player.getX());
    }

    @Test
    void testCombat() {
        player.takeDamage(30);
        assertEquals(70, player.getHealth());
        assertTrue(player.isAlive());

        player.takeDamage(80);
        assertEquals(0, player.getHealth());
        assertFalse(player.isAlive());
    }

    @Test
    void testHealing() {
        player.takeDamage(50);
        assertEquals(50, player.getHealth());

        player.heal(30);
        assertEquals(80, player.getHealth());

        player.heal(50); // Should cap at 100
        assertEquals(100, player.getHealth());
    }

    @Test
    void testInventoryManagement() {
        Item sword = new Item("Sword", "weapon", 10, "Sharp blade");
        Item potion = new Item("Potion", "potion", 30, "Healing potion");

        player.addItem(sword);
        assertEquals(1, player.getInventorySize());
        assertEquals(sword, player.getEquippedWeapon()); // Auto-equip first weapon

        player.addItem(potion);
        assertEquals(2, player.getInventorySize());
        assertTrue(player.hasPotion());
        assertEquals(potion, player.getPotion());

        player.removeItem(sword);
        assertEquals(1, player.getInventorySize());
        assertNull(player.getEquippedWeapon()); // Weapon unequipped when removed
    }
}