package org.example;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.entities.equipment.EquipmentSlot;
import org.example.entities.items.consumables.HealthPotion;
import org.example.entities.items.weapons.IronSword;
import org.example.service.EquipmentOutcome;
import org.example.service.ItemService;
import org.example.service.ItemUseOutcome;
import org.example.utils.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    private ItemService itemService;
    private Player player;

    @BeforeEach
    void setUp() {
        Random mockRandom = new Random(12345);
        RandomGenerator testGenerator = new RandomGenerator(mockRandom);
        itemService = new ItemService(testGenerator);

        player = new Player("TestPlayer", 100, new Position(1, 1));
    }

    // TEST 1: Testing using a health potion from the player's inventory.
    @Test
    void testUseHealthPotion() {

        player.takeDamage(50, 50); // Reduce player health to 50
        player.addToInventory(new HealthPotion(new Position(1, 1))); // Add a health potion to inventory

        ItemUseOutcome result = itemService.useItem(player, 0); // Use the health potion

        assertTrue(result.isSuccess()); // Check if use was successful
        assertEquals("Used: Health Potion", result.getMessage()); // Check success message
        assertEquals(80, player.getHealth()); // Health should increase by 30 (from 50 to 80)
        assertEquals(0, player.getInventory().size()); // Inventory should be empty after use
    }

    // TEST 2: Testing using an item with empty inventory.
    @Test
    void testUseFromEmptyInventory() {
        ItemUseOutcome result = itemService.useItem(player, 0); // Try to use an item from an empty inventory

        assertFalse(result.isSuccess()); // Should fail
        assertEquals("Your inventory is empty.", result.getMessage()); // Check failure message
    }

    //TEST 3: Testing using an item with an invalid index, and ensuring inventory remains unchanged.
    @Test
    void testUseItemWithInvalidIndex() {
        player.addToInventory(new HealthPotion(new Position(1, 1)));

        ItemUseOutcome result = itemService.useItem(player, 5); // Invalid index

        assertFalse(result.isSuccess()); // Should fail
        assertEquals("Invalid choice.", result.getMessage()); // Check failure message
        assertEquals(1, player.getInventory().size()); // Inventory should remain unchanged
    }

    // TEST 4: Testing equipping a sword from the player's inventory and confirming damage increase.
    @Test
    void testEquipSword() {
        int originalDamage = player.getDamage();
        IronSword sword = new IronSword((new Position(1, 1)));
        player.addToInventory(sword);

        EquipmentOutcome result = itemService.equipItem(player, 0); // Equip the sword

        assertTrue(result.isSuccess()); // Check if equip was successful
        assertEquals("Equipped: Iron Sword", result.getMessage()); // Check success message
        assertEquals(originalDamage + 20, player.getDamage()); // Damage should increase by 20
        assertEquals(0, player.getInventory().size()); // Inventory should be empty after equipping
        assertNotNull(player.getEquippedItem(EquipmentSlot.WEAPON)); // Sword should be equipped
    }

    // TEST 5: Testing equipping an item when no equipment is present in inventory.
    @Test
    void testEquipWithNoEquipment() {

        player.addToInventory(new HealthPotion(new Position(1, 1))); // Add a non-equipment item to inventory

        EquipmentOutcome result = itemService.equipItem(player, 0);

        assertFalse(result.isSuccess()); // Should fail
        assertEquals("You have no equipment to equip.", result.getMessage()); // Check failure message
    }

    // TEST 6: Edge case where index equals inventory size, ensuring it's handled as invalid. (Suggestion from codeRabbit).
    @Test
    void testUseItemIndexEqualsSizeIsInvalid() {
        player.addToInventory(new HealthPotion(new Position(1, 1))); // Inventory size is now 1
        int size = player.getInventory().size(); // size == 1
        ItemUseOutcome result = itemService.useItem(player, size); // Using index equal to size (1) which is invalid
        assertFalse(result.isSuccess()); // Should fail
        assertEquals("Invalid choice.", result.getMessage()); // Check failure message
        assertEquals(1, player.getInventory().size()); // Inventory should remain unchanged
    }
}
