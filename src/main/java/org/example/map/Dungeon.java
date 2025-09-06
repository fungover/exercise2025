package org.example.map;

import org.example.entities.*;
import org.example.entities.enemies.*;
import org.example.entities.items.*;

interface MapGenerator {
    Tile[][] generate (int width, int height);
}

public class Dungeon {
    private final int width;
    private final int height;
    private final Tile[][] tiles;
    private final Player player;
    private final MapGenerator mapGenerator;

    public Dungeon(int width, int height, Player player, MapGenerator mapGenerator) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (mapGenerator == null) {
            throw new IllegalArgumentException("Map generator cannot be null");
        }
        this.width = width;
        this.height = height;
        this.tiles = mapGenerator.generate(width, height);
        this.player = player;
        this.mapGenerator = mapGenerator;
        if (width > 2 || height > 3 && tiles[3][2].getType() == TileType.EMPTY) {
            player.moveTo(2, 3);
        } else {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (tiles[y][x].getType() == TileType.EMPTY) {
                        player.moveTo(x, y);
                        return;
                    }
                }
            }
            throw new IllegalStateException("No empty tile found for player placement");
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        return tiles[y][x];
    }

    public void movePlayer(int dx, int dy) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;
        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        if (!getTile(newX, newY).isPassable()) {
            throw new IllegalArgumentException("Position is not passable");
        }
        player.moveTo(newX, newY);
    }

    public void interact(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        int dx = Math.abs(x - player.getX());
        int dy = Math.abs(y - player.getY());
        if (dx + dy > 1) {
            throw new IllegalArgumentException("Position is too far away");
        }
        Tile tile = getTile(x, y);
        if (tile.getType() == TileType.ITEM) {
            Item item = tile.getItem();
            if (item != null) {
                player.addItem(item);
                tile.clearContents();
            }
        } else if (tile.getType() == TileType.ENEMY) {
            Enemy enemy = tile.getEnemy();
            if (enemy != null && enemy.isAlive()) {
                player.attack(enemy);
                if (!enemy.isAlive()) {
                    tile.clearContents();
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == player.getX() && y == player.getY()) {
                    sb.append('P');
                } else {
                    Tile tile = tiles[y][x];
                    switch (tile.getType()) {
                        case WALL:
                            sb.append('#');
                            break;
                        case ENEMY:
                            sb.append('E');
                            break;
                        case ITEM:
                            sb.append('I');
                            break;
                        case EMPTY:
                            sb.append('.');
                            break;
                    }
                }
                sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}

class BasicMapGenerator implements MapGenerator {
    @Override
    public Tile[][] generate(int width, int height) {
        Tile[][] tiles = new Tile[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = new Tile(TileType.EMPTY);
            }
        }
        // Add walls around the border
        for (int x = 0; x < width; x++) {
            tiles[0][x] = new Tile(TileType.WALL);
            tiles[height - 1][x] = new Tile(TileType.WALL);
        }
        for (int y = 0; y < height; y++) {
            tiles[y][0] = new Tile(TileType.WALL);
            tiles[y][width - 1] = new Tile(TileType.WALL);
        }
        // Add enemies and items
        if (width >= 3 && height >= 3) {
            tiles[1][1] = new Tile(new Goblin()); // ENEMY
            tiles[1][2] = new Tile(new Troll()); // ENEMY
            tiles[2][1] = new Tile(new Troll()); // ENEMY
            tiles[2][2] = new Tile(new Potion("Health Potion", 20)); // ITEM
        }
        return tiles;
    }
}