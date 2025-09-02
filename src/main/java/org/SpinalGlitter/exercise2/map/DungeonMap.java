package org.SpinalGlitter.exercise2.map;

import org.SpinalGlitter.exercise2.entities.Position;
import org.SpinalGlitter.exercise2.entities.Potion;
import org.SpinalGlitter.exercise2.entities.Tile;
import org.SpinalGlitter.exercise2.utils.RandomGeneration;
import org.SpinalGlitter.exercise2.utils.WorldObject;

import java.util.Map;
import java.util.Random;

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
        // En enkel karta: allt golv förutom väggar runt kanten
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

    public void printMap(Position playerPos, Map<Position, WorldObject> items) {
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                Position pos = new Position(x, y);

                if (playerPos.equals(pos)) {
                    System.out.print("🧑"); // Player
                } else if (items.containsKey(pos)) { // replace with items (org was potions)
                    /*System.out.print("💗"); // Potion*/
                    System.out.print(items.get(pos).getSymbol());
                } else {
                    System.out.print(grid[y][x].getSymbol());
                }
            }
            System.out.println();
        }
    }
}
