package org.example.map;

import org.example.entities.Position;

import java.util.Random;

public class MapGen {
    private final Random random;

    public  MapGen(long seed) {
        this.random = new Random(seed);
    }

    public MapGen() {
        this.random = new Random();
    }

    public Dungeon generate(int width, int height) {
        Dungeon dungeon = new Dungeon(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (random.nextDouble() < 0.2) {
                    dungeon.getTile(new Position(x, y)).setType(TileType.WALL);
                }
            }
        }
        return  dungeon;
    }
}
