package service;

import entities.Player;
import map.Dungeon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovementServiceTest {

    @Test
    void hittingWallKeepsPosition() {
        // Given: 10x10 dungeon has walls on borders; (y=0) is a wall.
        Player p = new Player("Test", 20, 1, 1);
        Dungeon d = new Dungeon(10, 10);

        // When: try to move north into the top border wall at y=0
        MovementService.move(p, d, "north");

        // Then: position unchanged
        assertEquals(1, p.getX());
        assertEquals(1, p.getY());
    }

    @Test
    void movingToWalkableTileUpdatesPosition() {
        // Given: inner tiles are walkable; (2,1) should be EMPTY by default
        Player p = new Player("Test", 20, 1, 1);
        Dungeon d = new Dungeon(10, 10);

        // When: move east into a walkable tile
        MovementService.move(p, d, "east");

        // Then: position updated
        assertEquals(2, p.getX());
        assertEquals(1, p.getY());
    }

    @Test
    void movingOutsideBoundsIsRejected() {
        // Given: player starts on the left border (x=0, which is a wall tile),
        // moving west would go to x = -1 (outside bounds).
        Player p = new Player("Test", 20, 0, 1);
        Dungeon d = new Dungeon(10, 10);

        // When: attempt to move outside the dungeon bounds
        MovementService.move(p, d, "west");

        // Then: position unchanged
        assertEquals(0, p.getX());
        assertEquals(1, p.getY());
    }
}
