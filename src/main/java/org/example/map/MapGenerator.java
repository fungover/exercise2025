package org.example.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.example.map.DungeonMap;
import org.example.map.Room;
import org.example.map.TileType;

/**
 * Seeded RNG dungeon generator. Current step: place non-overlapping rooms.
 * Future steps: connect rooms with corridors, boss placement, items, enemies.
 */
public final class MapGenerator {
    private final Random random;

    // Tunable parameters with descriptive names
    private final int minRoomSize;
    private final int maxRoomSize;
    private final int desiredRoomCount;
    private final int roomPadding;
    private final int maxPlacementAttempts;

    public MapGenerator(long seed,
                        int minRoomSize,
                        int maxRoomSize,
                        int desiredRoomCount,
                        int roomPadding,
                        int maxPlacementAttempts) {
        if (minRoomSize <= 1) throw new IllegalArgumentException("minRoomSize must be > 1");
        if (maxRoomSize < minRoomSize) throw new IllegalArgumentException("maxRoomSize must be >= minRoomSize");
        if (desiredRoomCount <= 0) throw new IllegalArgumentException("desiredRoomCount must be > 0");
        if (roomPadding < 0) throw new IllegalArgumentException("roomPadding must be >= 0");
        if (maxPlacementAttempts <= 0) throw new IllegalArgumentException("maxPlacementAttempts must be > 0");

        this.random = new Random(seed);
        this.minRoomSize = minRoomSize;
        this.maxRoomSize = maxRoomSize;
        this.desiredRoomCount = desiredRoomCount;
        this.roomPadding = roomPadding;
        this.maxPlacementAttempts = maxPlacementAttempts;
    }

    /** Builds a map by carving randomly-placed, non-overlapping rooms into an all-wall grid. */
    public DungeonMap generate(int mapWidth, int mapHeight) {
        DungeonMap map = new DungeonMap(mapWidth, mapHeight);
        List<Room> placedRooms = new ArrayList<>();

        int attemptsMade = 0;
        while (placedRooms.size() < desiredRoomCount && attemptsMade < maxPlacementAttempts) {
            attemptsMade++;

            int roomWidth  = randomBetween(minRoomSize, maxRoomSize);
            int roomHeight = randomBetween(minRoomSize, maxRoomSize);

            // Keep a 1-tile border so rooms don’t touch the map edge.
            int left = randomBetween(1, mapWidth  - roomWidth  - 2);
            int top  = randomBetween(1, mapHeight - roomHeight - 2);

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

        // Mark SPAWN in the first room for now (we'll improve later)
        if (!placedRooms.isEmpty()) {
            Room firstRoom = placedRooms.getFirst();
            map.tileAt(firstRoom.left() + 1, firstRoom.top() + 1).setType(TileType.SPAWN);
        }

        return map;
    }

    private void carveRoom(DungeonMap map, Room room) {
        for (int row = room.top(); row <= room.bottom(); row++) {
            for (int column = room.left(); column <= room.right(); column++) {
                map.tileAt(column, row).setType(TileType.FLOOR);
            }
        }
    }

    private int randomBetween(int inclusiveMin, int inclusiveMax) {
        return inclusiveMin + random.nextInt(inclusiveMax - inclusiveMin + 1);
    }
}
