package org.example.service.enemy;

import java.util.List;
import java.util.Random;

import org.example.map.DungeonMap;
import org.example.map.Room;
import org.example.map.TileType;

public class EnemyService {
    private final Random random;
    private final EnemyTable enemyTable;

    public EnemyService(long seed, EnemyTable enemyTable) {
        this.random = new Random(seed);
        this.enemyTable = enemyTable;
    }

    /** Scatters enemies across the given rooms, avoiding the spawn tile. */
    public void scatter(DungeonMap map, List<Room> rooms, int spawnX, int spawnY) {
        for (Room room : rooms) {
            int enemiesInRoom = randomBetween(0, 2);
            for (int i = 0; i < enemiesInRoom; i++) {
                for (int attempts = 0; attempts < 10; attempts++) {
                    int x = randomBetween(room.left(), room.right());
                    int y = randomBetween(room.top(), room.bottom());
                    var tile = map.tileAt(x, y);
                    if (tile.getType() != TileType.FLOOR) continue;
                    if (x == spawnX && y == spawnY) continue;
                    if (tile.hasEnemy()) continue;
                    tile.setEnemy(enemyTable.roll());
                    break;
                }
            }
        }
    }

    private int randomBetween(int inclusiveMin, int inclusiveMax) {
        if (inclusiveMax < inclusiveMin) return inclusiveMin; // guard for tiny rooms
        return inclusiveMin + random.nextInt(inclusiveMax - inclusiveMin + 1);
    }
}
