package org.example.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    private Item weapon;
    private Item potion;
    private Item key;
    private Item treasure;

    @BeforeEach
    void setUp() {
        weapon = new Item("Iron Sword", "weapon", 15, "A sharp iron sword");
        potion = new Item("Health Potion", "potion", 30, "Restores health");
        key = new Item("Golden Key", "key", 1, "Opens locked doors");
        treasure = new Item("Diamond", "treasure", 100, "Valuable gem");
    }

    @Test
    void testItemCreation() {
        assertEquals("Iron Sword", weapon.getName());
        assertEquals("weapon", weapon.getType());
        assertEquals(15, weapon.getEffectValue());
        assertEquals("A sharp iron sword", weapon.getDescription());
    }

    @Test
    void testItemTypes() {
        assertTrue(weapon.isWeapon());
        assertFalse(weapon.isPotion());
        assertFalse(weapon.isKey());
        assertFalse(weapon.isTreasure());

        assertTrue(potion.isPotion());
        assertFalse(potion.isWeapon());

        assertTrue(key.isKey());
        assertTrue(treasure.isTreasure());
    }

    @Test
    void testToString() {
        String expected = "Iron Sword (weapon) - A sharp iron sword";
        assertEquals(expected, weapon.toString());
    }
}