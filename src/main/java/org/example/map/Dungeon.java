package org.example.map;

import org.example.entities.*;

interface MapGenerator {
    Tile[][] generate (int width, int height);
}

public class Dungeon {
    private final int width;
    private final int height;
    private final Tile[][] tiles;
    private final Player player;
    private final MapGenerator mapGenerator;
}
