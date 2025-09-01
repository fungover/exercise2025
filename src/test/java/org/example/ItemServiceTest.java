package org.example;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.entities.items.consumables.HealthPotion;
import org.example.service.ItemService;
import org.example.service.ItemUseOutcome;
import org.example.utils.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
