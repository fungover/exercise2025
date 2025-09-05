package org.example.service.enemy;

import java.util.List;
import java.util.Random;

import org.example.entities.enemies.Boss;
import org.example.map.DungeonMap;
import org.example.map.Room;
import org.example.map.TileType;
import org.example.utils.Position;
import static org.example.utils.GridMath.manhattan;
import static org.example.utils.GridMath.topLeftCentered;
import static org.example.utils.Rng.between;

public class EnemyService {
    private final Random random;
    private final EnemyTable enemyTable;

    public EnemyService(long seed, EnemyTable enemyTable) {
        this.random = new Random(seed);
        this.enemyTable = enemyTable;
    }

    /** Scatters regular enemies across the given rooms, avoiding the spawn tile and existing enemies. */
    public void scatter(DungeonMap map, List<Room> roomList, int spawnXCoordinate, int spawnYCoordinate) {
        for (Room room : roomList) {
            int enemiesInThisRoom = between(random, 0, 2);
            for (int n = 0; n < enemiesInThisRoom; n++) {
                for (int attempts = 0; attempts < 10; attempts++) {
                    int x = between(random, room.left(), room.right());
                    int y = between(random, room.top(),  room.bottom());

                    var tile = map.tileAt(x, y);
                    if (tile.getType() != TileType.FLOOR) continue;
                    if (x == spawnXCoordinate && y == spawnYCoordinate) continue;
                    if (tile.hasEnemy()) continue;

                    tile.setEnemy(enemyTable.roll());
                    break;
                }
            }
        }
    }

    /** Place a 2×2 boss footprint in the room farthest from spawn (by Manhattan distance). */
    public void placeBoss2x2(DungeonMap map, List<Room> roomList, int spawnXCoordinate, int spawnYCoordinate) {
        if (roomList == null || roomList.isEmpty()) return;

        // Find farthest room
        Room farthestRoom = roomList.getFirst();
        int bestDistance = manhattan(spawnXCoordinate, spawnYCoordinate,
                farthestRoom.centerX(), farthestRoom.centerY());
        for (int index = 1; index < roomList.size(); index++) {
            Room candidateRoom = roomList.get(index);
            int candidateDistance = manhattan(spawnXCoordinate, spawnYCoordinate,
                    candidateRoom.centerX(), candidateRoom.centerY());
            if (candidateDistance > bestDistance) {
                bestDistance = candidateDistance;
                farthestRoom = candidateRoom;
            }
        }

        // Ensure there's space for a 2×2 footprint
        if (farthestRoom.width() < 2 || farthestRoom.height() < 2) return;

        // Compute a top-left that keeps the 2×2 boss fully inside the room, roughly centered
        Position topLeft = topLeftCentered(farthestRoom, 2, 2);
        int topLeftX = topLeft.x();
        int topLeftY = topLeft.y();

        Boss boss = new Boss();

        setBossTile(map, topLeftX, topLeftY, boss);
        setBossTile(map, topLeftX + 1, topLeftY, boss);
        setBossTile(map, topLeftX,topLeftY + 1, boss);
        setBossTile(map, topLeftX + 1, topLeftY + 1, boss);
    }

    private void setBossTile(DungeonMap map, int x, int y, Boss boss) {
        if (!map.isInside(x, y)) return;
        var tile = map.tileAt(x, y);
        tile.setType(TileType.BOSS);
        tile.setEnemy(boss);
    }
}
