package map;

import utils.Position;

public class Tile {
    private final Position position;
    private TileType type;

    public Tile (int x, int y, TileType type) {
        this.position = new Position(x, y);
        this.type = type;
    }

}
