package org.example;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.service.LootDropOutcome;
import org.example.service.LootService;
import org.example.utils.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
