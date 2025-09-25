package Map;

import Entities.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class DungeonMapTest {

    @Test
    void bordersAreWallsInteriorIsFloor() {
        DungeonMap map = new DungeonMap(6, 6);
        map.generateBasicLayout();


        assertFalse(map.tile(0, 0).getType().isWalkable());
        assertFalse(map.tile(5, 0).getType().isWalkable());
        assertFalse(map.tile(0, 5).getType().isWalkable());
        assertFalse(map.tile(5, 5).getType().isWalkable());


        assertTrue(map.tile(3, 3).getType().isWalkable());
    }

    @Test
    void inBoundsWorks() {
        DungeonMap map = new DungeonMap(4, 3);
        assertTrue(map.inBounds(0, 0));
        assertTrue(map.inBounds(3, 2));
        assertFalse(map.inBounds(-1, 0));
        assertFalse(map.inBounds(4, 0));
        assertFalse(map.inBounds(0, 3));
    }

    @Test
    void maxSizeIsEnforced() {

        int maxW = DungeonMap.MAX_WIDTH;
        int maxH = DungeonMap.MAX_HEIGHT;

        assertThrows(IllegalArgumentException.class, () -> new DungeonMap(maxW + 1, 10));
        assertThrows(IllegalArgumentException.class, () -> new DungeonMap(10, maxH + 1));


        new DungeonMap(maxW, maxH);
    }

    @Test
    void placePlayerAtStartPositionsPlayer() {
        DungeonMap map = new DungeonMap(6, 6);
        map.generateBasicLayout();

        Player p = new Player("Hero", 30, 5, 0, 0);
        map.placePlayerAtStart(p);

        assertEquals(1, p.getX());
        assertEquals(1, p.getY());
    }
}
