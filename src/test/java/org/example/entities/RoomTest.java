package org.example.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    @Test
    void testRoomExit() {
        Room startRoom = new Room("Start", "Starting room");
        Room northRoom = new Room("North", "Northern room");
        startRoom.addExit("north", northRoom);

        assertEquals(northRoom, startRoom.getExit("north"));
        assertNull(startRoom.getExit("south"));
        assertTrue(startRoom.hasExit("north"));
        assertFalse(startRoom.hasExit("east"));
    }
}