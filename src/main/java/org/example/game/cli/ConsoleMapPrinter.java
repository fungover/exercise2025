package org.example.game.cli;

import org.example.map.DungeonMap;
import org.example.map.TileType;

/** Utility to print a DungeonMap layout. Overlays: '@' (player) and '!' (items on floor). */
public final class ConsoleMapPrinter {
    private ConsoleMapPrinter() {}

    private static final char WALL_G   = '#';
    private static final char FLOOR_G  = '.';
    private static final char SPAWN_G  = 'S';
    private static final char BOSS_G   = 'B';
    private static final char ITEM_G   = '!';
    private static final char PLAYER_G = '@';

    /** Draw the map, overlaying the player at (playerX, playerY) if coordinates are >= 0. */
    public static void print(DungeonMap map, int playerX, int playerY) {
        StringBuilder out = new StringBuilder(map.height() * (map.width() + 1));
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                out.append(cellGlyph(map, x, y, playerX, playerY));
            }
            out.append('\n');
        }
        System.out.print(out);
    }

    /** Compose the final glyph at a cell using layering: base -> items -> player. */
    private static char cellGlyph(DungeonMap map, int x, int y, int playerX, int playerY) {
        // Player overlay wins (so standing on loot still shows '@')
        if (x == playerX && y == playerY) return PLAYER_G;

        var tile = map.tileAt(x, y);

        // Item overlay (only on walkable ground; identity hidden until inspected)
        if (tile.getType() == TileType.FLOOR && tile.hasItems()) return ITEM_G;

        // Base ground glyph
        return switch (tile.getType()) {
            case WALL  -> WALL_G;
            case FLOOR -> FLOOR_G;
            case SPAWN -> SPAWN_G;
            case BOSS  -> BOSS_G;
        };
    }
}
