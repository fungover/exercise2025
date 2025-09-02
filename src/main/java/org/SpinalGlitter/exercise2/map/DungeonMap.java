package org.SpinalGlitter.exercise2.map;

import org.SpinalGlitter.exercise2.entities.Enemy;
import org.SpinalGlitter.exercise2.entities.Position;
import org.SpinalGlitter.exercise2.entities.Potion;
import org.SpinalGlitter.exercise2.entities.Tile;

import java.util.Map;

public class DungeonMap {
    private final Tile[][] grid;
    private final int width;
    private final int height;
    public int getWidth()  { return width; }
    public int getHeight() { return height; }


    public DungeonMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Tile[height][width];

        generateMap();
    }

    private void generateMap() {
        // Allt golv förutom väggar runt kanten
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    grid[y][x] = new Tile(false, "🧱"); // vägg
                } else {
                    grid[y][x] = new Tile(true, "⬜");  // golv
                }
            }
        }
    }

    public boolean canMoveTo(Position pos) {
        if (pos.x() < 0 || pos.y() < 0 || pos.x() >= width || pos.y() >= height) {
            return false;
        }
        return grid[pos.y()][pos.x()].isWalkable();
    }

    public void setWall(Position pos) {
        if (pos.x() <= 0 || pos.y() <= 0 || pos.x() >= width - 1 || pos.y() >= height - 1) {
            return; // undvik att sätta väggar på kanten
        }
        grid[pos.y()][pos.x()] = new Tile(false, "🧱");
    }

    public boolean isFloor(Position pos) {
        if (pos.x() < 0 || pos.y() < 0 || pos.x() >= width || pos.y() >= height) {
            return false;
        }
        return grid[pos.y()][pos.x()].isWalkable();
    }

    // Befintlig variant (endast potions)
    public void printMap(Position playerPos, Map<Position, Potion> potions) {
        printMap(playerPos, potions, null);  // skickar null som enemies-map
    }

    public void printMap(Position playerPos, Map<Position, Potion> potions, Map<Position, Enemy> enemies) {
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                Position pos = new Position(x, y);

                if (playerPos.equals(pos)) {
                    System.out.print("🧑");
                } else if (enemies != null && enemies.containsKey(pos)) {
                    Enemy e = enemies.get(pos);

                    String s = switch (e.getName()) {
                        case "Skeleton" -> "☠️";
                        case "Goblin" -> "👹";
                        default -> "👾";
                    };
                    System.out.print(s);
                } else if (potions != null && potions.containsKey(pos)) {
                    System.out.print("💗");
                } else {
                    System.out.print(grid[y][x].getSymbol());
                }
            }
            System.out.println();
        }
    }
}
