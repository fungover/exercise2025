import org.example.entities.Position;
import org.example.map.Dungeon;
import org.example.map.MapGen;
import org.example.map.TileType;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MapGenTest {

    @Test
    void testDungeonSize() {
        int width = 12;
        int height = 12;
        MapGen generator = new MapGen(42);
        Dungeon dungeon = generator.generate(width, height);

        assertEquals(height, dungeon.getHeight(), "Dungeon height should match");
        assertEquals(width, dungeon.getWidth(), "Dungeon width should match");
    }

    @Test
    void testDungeonContainsTiles() {
        MapGen generator = new MapGen();
        Dungeon dungeon = generator.generate(10, 10);

        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                Position pos = new Position(x, y);
                assertNotNull(dungeon.getTile(pos), "Tile at " + pos + " should not be null");
            }
        }
    }

    @Test
    void testWallProbability() {
        MapGen generator = new MapGen(123);
        Dungeon dungeon = generator.generate(100, 100);

        long wallCount = 0;
        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                if (dungeon.getTile(new Position(x, y)).getType() == TileType.WALL) {
                    wallCount++;
                }
            }
        }

        double wallRatio = wallCount / 10000.0;
        assertTrue(wallRatio > 0.15 && wallRatio < 0.25, "Wall ratio should be roughly 20%");
    }
}
