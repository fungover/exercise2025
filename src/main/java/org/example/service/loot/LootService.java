package org.example.service.loot;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.example.entities.items.Item;
import org.example.map.DungeonMap;
import org.example.map.Room;
import org.example.map.TileType;

/** Places rolled loot onto the map (rooms, avoiding SPAWN). */
public final class LootService {
    private final LootTable table;
    private final Random rng; // for placement/counts

    public LootService(long seed, LootTable table) {
        this.table = table;
        this.rng = new Random(seed ^ 0x9E3779B97F4A7C15L);
    }

    /** Drop 0..2 items per room at random floor tiles, avoiding SPAWN. */
    public void scatter(DungeonMap map, List<Room> rooms, int spawnX, int spawnY) {
        for (Room room : rooms) {
            int drops = between(0, 2);
            for (int i = 0; i < drops; i++) {
                Optional<Item> maybe = table.roll();
                if (maybe.isEmpty()) continue;

                // Try a few random spots inside the room
                for (int attempts = 0; attempts < 10; attempts++) {
                    int x = between(room.left(), room.right());
                    int y = between(room.top(), room.bottom());
                    var tile = map.tileAt(x, y);
                    if (tile.getType() != TileType.FLOOR) continue;
                    if (x == spawnX && y == spawnY) continue; // don't cover spawn
                    tile.addItem(maybe.get());
                    break;
                }
            }
        }
    }

    private int between(int min, int max) {
        return min + rng.nextInt(max - min + 1);
    }
}
