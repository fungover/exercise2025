package org.example.map;

import org.example.entities.*;
import org.example.entities.enemies.*;
import org.example.entities.items.*;
import org.example.service.ItemService;
import org.example.service.MovementService;
import org.example.utils.InputValidator;
import org.example.service.CombatService;

public class Dungeon {
    private final int width;
    private final int height;
    private final Tile[][] tiles;
    private final Player player;
    private final MapGenerator mapGenerator;
    private final MovementService movementService;
    private final CombatService combatService;
    private final ItemService itemService;

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
        InputValidator validator = new InputValidator(this);
        this.movementService = new MovementService(this, validator);
        this.combatService = new CombatService(this, validator);
        this.itemService = new ItemService(this, validator);
        if (width > 2 && height > 3 && tiles[3][2].getType() == TileType.EMPTY) {
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
        movementService.movePlayer(dx, dy, player);
    }

    public void interact(int x, int y) {
        InputValidator validator = new InputValidator(this);
        if (!validator.isValidInteraction(x, y, player)) {
            throw new IllegalArgumentException("Invalid interaction");
        }
        Tile tile = getTile(x, y);
        if (tile.getType() == TileType.ITEM) {
            itemService.pickUpItem(x, y, player);
        } else if (tile.getType() == TileType.ENEMY) {
            combatService.engageCombat(x, y, player);
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

