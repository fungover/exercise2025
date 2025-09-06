package org.example.map;

import org.example.entities.Tile;
import org.example.entities.TileType;
import org.example.entities.enemies.Goblin;
import org.example.entities.enemies.Troll;
import org.example.entities.items.Potion;
import org.example.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class BasicMapGenerator implements MapGenerator {
    @Override
    public Tile[][] generate(int width, int height) {
        Tile[][] tiles = new Tile[height][width];
        RandomGenerator rng = new RandomGenerator();
        // Initialize all tiles as EMPTY
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = new Tile(TileType.EMPTY);
            }
        }
        // Add walls around border
        for (int x = 0; x < width; x++) {
            tiles[0][x] = new Tile(TileType.WALL);
            tiles[height - 1][x] = new Tile(TileType.WALL);
        }
        for (int y = 0; y < height; y++) {
            tiles[y][0] = new Tile(TileType.WALL);
            tiles[y][width - 1] = new Tile(TileType.WALL);
        }
        // Add enemies and items at random positions
        if (width >= 3 && height >= 3) {
            List<int[]> availablePositions = new ArrayList<>();
            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (tiles[y][x].getType() == TileType.EMPTY && !(x == 2 && y == 3)) { // Exclude player start
                        availablePositions.add(new int[]{x, y});
                    }
                }
            }
            // Place Goblin
            if (!availablePositions.isEmpty()) {
                int[] pos = rng.nextElement(availablePositions.toArray(new int[0][]));
                tiles[pos[1]][pos[0]] = new Tile(new Goblin());
                System.out.println("Set tile[" + pos[1] + "][" + pos[0] + "]: " + tiles[pos[1]][pos[0]]);
                availablePositions.removeIf(p -> p[0] == pos[0] && p[1] == pos[1]);
            }
            // Place first Troll
            if (!availablePositions.isEmpty()) {
                int[] pos = rng.nextElement(availablePositions.toArray(new int[0][]));
                tiles[pos[1]][pos[0]] = new Tile(new Troll());
                System.out.println("Set tile[" + pos[1] + "][" + pos[0] + "]: " + tiles[pos[1]][pos[0]]);
                availablePositions.removeIf(p -> p[0] == pos[0] && p[1] == pos[1]);
            }
            // Place second Troll
            if (!availablePositions.isEmpty()) {
                int[] pos = rng.nextElement(availablePositions.toArray(new int[0][]));
                tiles[pos[1]][pos[0]] = new Tile(new Troll());
                System.out.println("Set tile[" + pos[1] + "][" + pos[0] + "]: " + tiles[pos[1]][pos[0]]);
                availablePositions.removeIf(p -> p[0] == pos[0] && p[1] == pos[1]);
            }
            // Place Potion
            if (!availablePositions.isEmpty()) {
                int[] pos = rng.nextElement(availablePositions.toArray(new int[0][]));
                tiles[pos[1]][pos[0]] = new Tile(new Potion("Health Potion", 20));
                System.out.println("Set tile[" + pos[1] + "][" + pos[0] + "]: " + tiles[pos[1]][pos[0]]);
            }
        }
        return tiles;
    }
}
