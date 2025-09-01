package org.example;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.service.LootDropOutcome;
import org.example.service.LootService;
import org.example.utils.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LootServiceTest {

    private LootService lootService; // Instance of the service to be tested
    private Player player; // Player instance for testing

    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer", 100, new Position(1, 1)); // Create a player with an empty inventory
    }

    // TEST 1: Testing loot drop with a mocked Random to ensure an item is always dropped.
    @Test
    void testGuaranteedLootDrop() {

        Random mockRandom = new Random() { // Mock Random to always return 0, ensuring loot drop
            @Override
            public int nextInt(int bound) {
                if (bound == 100) return 0; // Ensure loot drop (0 < 50)
                return 0; // Always return first item in list
            }
        };
        RandomGenerator testGenerator = new RandomGenerator(mockRandom); // Inject mock Random into RandomGenerator to control randomness
        lootService = new LootService(testGenerator); // Create LootService with the test RandomGenerator

        LootDropOutcome result = lootService.tryDropRandomLoot(player); //Run the loot-logic, trying to randomly drop loot

        assertTrue(result.isItemDropped()); // Assert that an item was dropped
        assertEquals(1, player.getInventory().size()); // Assert that the player's inventory now has 1 item
        assertTrue(result.getMessage().contains("dropped")); // Assert that the message indicates an item was dropped
    }

    // TEST 2: Testing when loot does not drop.
    @Test
    void testNoLootDrop() {

        Random mockRandom = new Random() {
            @Override
            public int nextInt(int bound) {
                return 50; // Ensure no loot drop (50 >= 50)
            }
        };
        RandomGenerator testGenerator = new RandomGenerator(mockRandom);
        lootService = new LootService(testGenerator);

        LootDropOutcome result = lootService.tryDropRandomLoot(player); //Run the loot-logic, trying to randomly drop loot

        assertFalse(result.isItemDropped()); // Assert that no item was dropped
        assertEquals("No loot dropped.", result.getMessage()); // Assert that the message indicates no loot was dropped
        assertEquals(0, player.getInventory().size()); // Assert that the player's inventory is still empty

    }

    @Test
    void testSwordDrop() {

        Random mockRandom = new Random() {
            public int nextInt(int bound) {
                if (bound == 100) return 0;
                return 0; // Always return first item in list (SWORD)
            }
        };
        RandomGenerator testGenerator = new RandomGenerator(mockRandom);
        LootService testLootService = new LootService(testGenerator);

        LootDropOutcome result = testLootService.tryDropRandomLoot(player);

        assertTrue(result.isItemDropped()); // Assert that an item was dropped
        assertTrue(result.getMessage().contains("Iron Sword")); // Assert that the message indicates a sword was dropped
        assertTrue(testLootService.isSwordDropped()); // Assert that the sword drop flag is set to true
        assertEquals(1, player.getInventory().size()); // Assert that the player's inventory now has 1 item
    }

    @Test
    void testHelmetDrop() {

        Random mockRandom = new Random() {
            public int nextInt(int bound) {
                if (bound == 100) return 0;
                return 1; // Always return second item in list (HELMET)
            }
        };
        RandomGenerator testGenerator = new RandomGenerator(mockRandom);
        LootService testLootService = new LootService(testGenerator);

        LootDropOutcome result = testLootService.tryDropRandomLoot(player);

        assertTrue(result.isItemDropped()); // Assert that an item was dropped
        assertTrue(result.getMessage().contains("Iron Helm")); // Assert that the message indicates a helmet was dropped
        assertTrue(testLootService.isHelmetDropped()); // Assert that the helmet drop flag is set to true
        assertEquals(1, player.getInventory().size()); // Assert that the player's inventory now has 1 item
    }

    @Test
    void testChestplateDrop() {

        Random mockRandom = new Random() {
            public int nextInt(int bound) {
                if (bound == 100) return 0;
                return 2; // Always return third item in list (CHESTPLATE)
            }
        };
        RandomGenerator testGenerator = new RandomGenerator(mockRandom);
        LootService testLootService = new LootService(testGenerator);

        LootDropOutcome result = testLootService.tryDropRandomLoot(player);

        assertTrue(result.isItemDropped()); // Assert that an item was dropped
        assertTrue(result.getMessage().contains("Iron Chestplate")); // Assert that the message indicates a chestplate was dropped
        assertTrue(testLootService.isChestplateDropped()); // Assert that the chestplate drop flag is set to true
        assertEquals(1, player.getInventory().size()); // Assert that the player's inventory now has 1 item
    }

    @Test
    void testBootsDrop() {

        Random mockRandom = new Random() {
            public int nextInt(int bound) {
                if (bound == 100) return 0;
                return 3; // Always return fourth item in list (BOOTS)
            }
        };
        RandomGenerator testGenerator = new RandomGenerator(mockRandom);
        LootService testLootService = new LootService(testGenerator);

        LootDropOutcome result = testLootService.tryDropRandomLoot(player);

        assertTrue(result.isItemDropped()); // Assert that an item was dropped
        assertTrue(result.getMessage().contains("Iron Boots")); // Assert that the message indicates boots were dropped
        assertTrue(testLootService.isBootsDropped()); // Assert that the boots drop flag is set to true
        assertEquals(1, player.getInventory().size()); // Assert that the player's inventory now has 1 item
    }

}
