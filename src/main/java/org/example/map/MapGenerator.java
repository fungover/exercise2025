package org.example.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.example.service.enemy.DefaultEnemyTable;
import org.example.service.enemy.EnemyService;
import org.example.service.loot.DefaultLootTable;
import org.example.service.loot.LootService;

import static org.example.utils.GridMath.manhattan;
import static org.example.utils.Rng.between;

public final class MapGenerator {
    private final Random random;
    private final LootService lootService;
    private final int minRoomSize;
    private final int maxRoomSize;
    private final int desiredRoomCount;
    private final int roomPadding;
    private final int maxPlacementAttempts;
    private final EnemyService enemyService;
    /** Builds a default LootService wired to DefaultLootTable (seeded). */
    public MapGenerator(long seed,
                        int minRoomSize,
                        int maxRoomSize,
                        int desiredRoomCount,
                        int roomPadding,
                        int maxPlacementAttempts) {
        this(seed, minRoomSize, maxRoomSize, desiredRoomCount, roomPadding, maxPlacementAttempts,
                new LootService(seed, new DefaultLootTable(seed)), new EnemyService(seed, new DefaultEnemyTable(seed)));
    }

    /** Pass any LootService (great for tests or custom tables). */
    public MapGenerator(long seed,
                        int minRoomSize,
                        int maxRoomSize,
                        int desiredRoomCount,
                        int roomPadding,
                        int maxPlacementAttempts,
                        LootService lootService,
                        EnemyService enemyService) {
        if (minRoomSize <= 1) throw new IllegalArgumentException("minRoomSize must be > 1");
        if (maxRoomSize < minRoomSize) throw new IllegalArgumentException("maxRoomSize must be >= minRoomSize");
        if (desiredRoomCount <= 0) throw new IllegalArgumentException("desiredRoomCount must be > 0");
        if (roomPadding < 0) throw new IllegalArgumentException("roomPadding must be >= 0");
        if (maxPlacementAttempts <= 0) throw new IllegalArgumentException("maxPlacementAttempts must be > 0");

        this.random = new Random(seed);
        this.lootService = lootService;
        this.enemyService = enemyService;
        this.minRoomSize = minRoomSize;
        this.maxRoomSize = maxRoomSize;
        this.desiredRoomCount = desiredRoomCount;
        this.roomPadding = roomPadding;
        this.maxPlacementAttempts = maxPlacementAttempts;
    }

    /** Builds a map: rooms + corridors; marks SPAWN; scatters loot. */
    public DungeonMap generate(int mapWidth, int mapHeight) {
        DungeonMap map = new DungeonMap(mapWidth, mapHeight);
        List<Room> placedRooms = placeRooms(map, mapWidth, mapHeight);

        if (placedRooms.size() >= 2) {
            connectRoomsWithCorridors(map, placedRooms);
        }

        if (!placedRooms.isEmpty()) {
            Room firstRoom = placedRooms.getFirst();
            int spawnX = firstRoom.left() + 1;
            int spawnY = firstRoom.top() + 1;
            map.tileAt(spawnX, spawnY).setType(TileType.SPAWN);

            lootService.scatter(map, placedRooms, spawnX, spawnY);
            enemyService.scatter(map, placedRooms, spawnX, spawnY);
            enemyService.placeBoss2x2(map, placedRooms, spawnX, spawnY);}
        return map;
    }

    private List<Room> placeRooms(DungeonMap map, int mapWidth, int mapHeight) {
        List<Room> placedRooms = new ArrayList<>();
        int attemptsMade = 0;

        while (placedRooms.size() < desiredRoomCount && attemptsMade < maxPlacementAttempts) {
            attemptsMade++;

            int roomWidth  = between(random, minRoomSize, maxRoomSize);
            int roomHeight = between(random, minRoomSize, maxRoomSize);

            // Keep a 1-tile border so rooms don’t touch the map edge.
            int left = between(random, 1, Math.max(1, mapWidth  - roomWidth  - 2));
            int top  = between(random, 1, Math.max(1, mapHeight - roomHeight - 2));

            Room candidate = new Room(left, top, roomWidth, roomHeight);

            boolean overlaps = false;
            for (Room existing : placedRooms) {
                if (existing.intersectsWith(candidate, roomPadding)) {
                    overlaps = true;
                    break;
                }
            }
            if (overlaps) continue;

            carveRoom(map, candidate);
            placedRooms.add(candidate);
        }
        return placedRooms;
    }

    private void carveRoom(DungeonMap map, Room room) {
        for (int row = room.top(); row <= room.bottom(); row++) {
            for (int column = room.left(); column <= room.right(); column++) {
                map.tileAt(column, row).setType(TileType.FLOOR);
            }
        }
    }

    /** Connect each room to the nearest already-placed room with an L-shaped corridor. */
    private void connectRoomsWithCorridors(DungeonMap map, List<Room> roomsInPlacementOrder) {
        for (int index = 1; index < roomsInPlacementOrder.size(); index++) {
            Room currentRoom = roomsInPlacementOrder.get(index);
            Room nearestRoom = findNearestRoom(currentRoom, roomsInPlacementOrder, index);

            int startX  = currentRoom.centerX();
            int startY  = currentRoom.centerY();
            int targetX = nearestRoom.centerX();
            int targetY = nearestRoom.centerY();

            boolean carveHorizontalFirst = random.nextBoolean();
            if (carveHorizontalFirst) {
                carveHorizontalCorridor(map, startX, targetX, startY);
                carveVerticalCorridor(map, startY, targetY, targetX);
            } else {
                carveVerticalCorridor(map, startY, targetY, startX);
                carveHorizontalCorridor(map, startX, targetX, targetY);
            }
        }
    }

    private Room findNearestRoom(Room referenceRoom, List<Room> placedRooms, int searchExclusiveEnd) {
        Room nearestRoom = placedRooms.getFirst();
        int bestDistance = manhattan(referenceRoom.centerX(), referenceRoom.centerY(),
                nearestRoom.centerX(),  nearestRoom.centerY());

        for (int i = 1; i < searchExclusiveEnd; i++) {
            Room candidateRoom = placedRooms.get(i);
            int distance = manhattan(referenceRoom.centerX(), referenceRoom.centerY(),
                    candidateRoom.centerX(), candidateRoom.centerY());
            if (distance < bestDistance) {
                bestDistance = distance;
                nearestRoom = candidateRoom;
            }
        }
        return nearestRoom;
    }

    private void setFloorIfWall(DungeonMap map, int x, int y) {
        if (!map.isInside(x, y)) return;
        var tile = map.tileAt(x, y);
        if (tile.getType() == TileType.WALL) tile.setType(TileType.FLOOR);
    }

    private void carveHorizontalCorridor(DungeonMap map, int fromX, int toX, int fixedY) {
        int startColumn = Math.min(fromX, toX);
        int endColumn   = Math.max(fromX, toX);
        for (int column = startColumn; column <= endColumn; column++) {
            setFloorIfWall(map, column, fixedY);
        }
    }

    private void carveVerticalCorridor(DungeonMap map, int fromY, int toY, int fixedX) {
        int startRow = Math.min(fromY, toY);
        int endRow   = Math.max(fromY, toY);
        for (int row = startRow; row <= endRow; row++) {
            setFloorIfWall(map, fixedX, row);
        }
    }
}