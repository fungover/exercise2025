package org.example.map;

import org.example.entities.items.Item;
import org.example.service.loot.DefaultLootFactory;
import org.example.service.loot.LootFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/** Next steps (later): boss placement, enemies. */
public final class MapGenerator {
    private final Random random;
    private final LootFactory lootFactory;
    private final int minRoomSize;
    private final int maxRoomSize;
    private final int desiredRoomCount;
    private final int roomPadding;
    private final int maxPlacementAttempts;

    // Builds a default loot table from the same seed
    public MapGenerator(long seed,
                        int minRoomSize,
                        int maxRoomSize,
                        int desiredRoomCount,
                        int roomPadding,
                        int maxPlacementAttempts) {
        this(seed, minRoomSize, maxRoomSize, desiredRoomCount, roomPadding, maxPlacementAttempts,
                new DefaultLootFactory(seed));
    }

    // Pass any LootFactory (great for tests or custom tables)
    public MapGenerator(long seed,
                        int minRoomSize,
                        int maxRoomSize,
                        int desiredRoomCount,
                        int roomPadding,
                        int maxPlacementAttempts,
                        LootFactory lootFactory) {
        if (minRoomSize <= 1) throw new IllegalArgumentException("minRoomSize must be > 1");
        if (maxRoomSize < minRoomSize) throw new IllegalArgumentException("maxRoomSize must be >= minRoomSize");
        if (desiredRoomCount <= 0) throw new IllegalArgumentException("desiredRoomCount must be > 0");
        if (roomPadding < 0) throw new IllegalArgumentException("roomPadding must be >= 0");
        if (maxPlacementAttempts <= 0) throw new IllegalArgumentException("maxPlacementAttempts must be > 0");

        this.random = new Random(seed);
        this.lootFactory = lootFactory;
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

            scatterLoot(map, placedRooms, spawnX, spawnY);
        }

        return map;
    }

    private List<Room> placeRooms(DungeonMap map, int mapWidth, int mapHeight) {
        List<Room> placedRooms = new ArrayList<>();

        int attemptsMade = 0;
        while (placedRooms.size() < desiredRoomCount && attemptsMade < maxPlacementAttempts) {
            attemptsMade++;

            int roomWidth  = randomBetween(minRoomSize, maxRoomSize);
            int roomHeight = randomBetween(minRoomSize, maxRoomSize);

            // Keep a 1-tile border so rooms don’t touch the map edge.
            int left = randomBetween(1, Math.max(1, mapWidth  - roomWidth  - 2));
            int top  = randomBetween(1, Math.max(1, mapHeight - roomHeight - 2));

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
            Room current = roomsInPlacementOrder.get(index);
            Room nearest = findNearestRoom(current, roomsInPlacementOrder, index);

            int startX = roomCenterX(current);
            int startY = roomCenterY(current);
            int targetX = roomCenterX(nearest);
            int targetY = roomCenterY(nearest);

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

    private Room findNearestRoom(Room reference, List<Room> placed, int searchExclusiveEnd) {
        Room nearest = placed.getFirst();
        int bestDistance = gridStepsBetween(
                roomCenterX(reference), roomCenterY(reference),
                roomCenterX(nearest), roomCenterY(nearest));

        for (int i = 1; i < searchExclusiveEnd; i++) {
            Room candidate = placed.get(i);
            int distance = gridStepsBetween(
                    roomCenterX(reference), roomCenterY(reference),
                    roomCenterX(candidate), roomCenterY(candidate));
            if (distance < bestDistance) {
                bestDistance = distance;
                nearest = candidate;
            }
        }
        return nearest;
    }

    /** Drop 0..2 items per room at random floor tiles, avoiding SPAWN. */
    private void scatterLoot(DungeonMap map, List<Room> rooms, int spawnX, int spawnY) {
        for (Room room : rooms) {
            int drops = randomBetween(0, 2);
            for (int i = 0; i < drops; i++) {
                Optional<Item> maybe = lootFactory.roll();
                if (maybe.isEmpty()) continue;

                // Try a few random tiles inside the room until we find a safe spot
                for (int attempts = 0; attempts < 10; attempts++) {
                    int x = randomBetween(room.left(), room.right());
                    int y = randomBetween(room.top(), room.bottom());
                    var tile = map.tileAt(x, y);
                    if (tile.getType() != TileType.FLOOR) continue;
                    if (x == spawnX && y == spawnY) continue; // don't cover spawn
                    tile.addItem(maybe.get());
                    break;
                }
            }
        }
    }

    private int roomCenterX(Room room) {
        return room.left() + room.width() / 2;
    }

    private int roomCenterY(Room room) {
        return room.top() + room.height() / 2;
    }

    private int gridStepsBetween(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
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

    private int randomBetween(int inclusiveMin, int inclusiveMax) {
        if (inclusiveMax < inclusiveMin) return inclusiveMin; // guard for tiny maps
        return inclusiveMin + random.nextInt(inclusiveMax - inclusiveMin + 1);
    }
}
