package map;

import utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonMap {
    private final int width;
    private final int height;
    private final Tile[][] tiles;
    private final List<Room> rooms = new ArrayList<>();
    private final Random random = new Random();

    public DungeonMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = new Tile(x, y, TileType.WALL);
            }
        }
    }

    public void generate(int maxRooms, int roomMinSize, int roomMaxSize) {
        for (int i = 0; i < maxRooms; i++) {
            int w = rand(roomMinSize, roomMaxSize);
            int h = rand(roomMinSize, roomMaxSize);
            int x = rand(1, Math.max(1, width  - w - 2));
            int y = rand(1, Math.max(1, height - h - 2));

            Room room = new Room(x, y, w, h);

            boolean overlaps = false;
            for (Room r : rooms) {
                if (r.isOverlapping(room)) {
                    overlaps = true;
                    break;
                }
            }
            if (overlaps) {
                continue;
            }

            createRoom(room);

            if (!rooms.isEmpty()) {
                Position a = rooms.get(rooms.size() - 1).center();
                Position b = room.center();
                createHorizontal(a.getX(), b.getX(), a.getY());
                createVertical(a.getY(), b.getY(), b.getX());
            }

            rooms.add(room);
        }
    }

    private void createRoom(Room r) {
        for (int y = r.top(); y <= r.bottom(); y++) {
            for (int x = r.left(); x <= r.right(); x++) {
                tiles[y][x].setType(TileType.FLOOR);
            }
        }
    }

    private void createHorizontal(int x1, int x2, int y) {
        int start = Math.min(x1, x2);
        int end   = Math.max(x1, x2);
        for (int x = start; x <= end; x++) {
            if (inBounds(x, y)) {
                tiles[y][x].setType(TileType.FLOOR);
            }
        }
    }

    private void createVertical(int y1, int y2, int x) {
        int start = Math.min(y1, y2);
        int end   = Math.max(y1, y2);
        for (int y = start; y <= end; y++) {
            if (inBounds(x, y)) {
                tiles[y][x].setType(TileType.FLOOR);
            }
        }
    }

    private int rand(int min, int max) {
        if (max < min) return min;
        return random.nextInt(max - min + 1) + min;
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    public Position findFirstWalkable() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x].isWalkable()) {
                    return new Position(x, y);
                }
            }
        }
        return null;
    }

    public Position randomEmptyFloor(int attempts) {
        for (int i = 0; i < attempts; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (inBounds(x, y) && tiles[y][x].isEmpty()) {
                return new Position(x, y);
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x].isEmpty()) {
                    return new Position(x, y);
                }
            }
        }
        return null;
    }

    public void printToConsole(entities.Player player) {
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < width; x++) {
                if (player != null
                        && player.getPosition() != null
                        && player.getPosition().getX() == x
                        && player.getPosition().getY() == y) {
                    line.append('@');
                } else {
                    line.append(tiles[y][x].toString());
                }
            }
            System.out.println(line);
        }
    }
}
