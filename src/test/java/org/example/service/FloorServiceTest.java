package org.example.service;

import org.example.map.Dungeon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FloorServiceTest {

    @Test
    void starts_on_floor_1() {
        FloorService floorService = new FloorService(2);
        assertEquals(1, floorService.getCurrentFloor());
        assertFalse(floorService.isFinalFloor());
    }

    @Test
    void can_advance_to_next_floor() {
        FloorService floors = new FloorService(2);

        Dungeon floor2 = floors.advance();
        assertEquals(2, floors.getCurrentFloor());
        assertTrue(floors.isFinalFloor());
        assertNotNull(floor2);
    }

    @Test
    void cannot_advance_past_final_floor() {
        FloorService floors = new FloorService(2);

        floors.advance();
        assertThrows(IllegalStateException.class, floors::advance);
    }
}
