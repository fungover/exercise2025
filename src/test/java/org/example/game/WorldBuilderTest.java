package org.example.game;

import org.example.entities.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldBuilderTest {

    @Test
    void testWorldCreation() {
        Room startRoom = WorldBuilder.createWorld();

        // Basic validation
        assertNotNull(startRoom);
        // Connectivity
        assertTrue(startRoom.hasExit("east") || startRoom.hasExit("west"));
    }
}
