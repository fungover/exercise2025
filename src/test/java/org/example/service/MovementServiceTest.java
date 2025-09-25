package org.example.service;


import org.example.entities.MockPlayer;
import org.example.map.mockDungeonGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MovementServiceTest {

    @Test void moveNorth_Valid() {
        // (2,2) is player pos, (2,1) must be valid
        boolean[][] map = {{true, true, true, true, true}, {true, false, true,
          true, true}, {false, true, true, true, true}, {true, true, true, true,
          true}, {true, true, true, true, true},};

        var dungeon = new mockDungeonGenerator(map);
        var player = new MockPlayer(2, 2);

        boolean moved = MovementService.movePlayer(player, "north", dungeon);

        assertTrue(moved);
        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test void moveEast_intoWall() {
        boolean[][] map = {{true, true, false, true, true}, {true, true, false,
          true, true}, {true, true, false, true, true}, {true, true, true, true,
          true}, {true, true, true, true, true},};

        var dungeon = new mockDungeonGenerator(map);
        var player = new MockPlayer(1, 1); // (2,1) is a wall
        boolean moved = MovementService.movePlayer(player, "east", dungeon);

        assertFalse(moved);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test void moveInvalidDirection() {
        boolean[][] map = {{true, true, true, true, true}, {true, true, true, true
          , true}, {true, true, true, true, true}, {true, true, true, true, true},
          {true, true, true, true, true},};
        var dungeon = new mockDungeonGenerator(map);
        var player = new MockPlayer(1, 1); // (2,1) is a wall
        boolean moved = MovementService.movePlayer(player, "poop", dungeon);

        assertFalse(moved);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }
}