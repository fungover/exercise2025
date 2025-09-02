package org.example.game;

import org.example.map.DungeonMap;
import org.example.map.TileType;

/** Utility to print a DungeonMap for quick preview/testing. */
public final class ConsoleMapPrinter {
    private ConsoleMapPrinter() {}

    public static void print(DungeonMap map) {
        StringBuilder out = new StringBuilder();
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                var tile = map.tileAt(x, y);
                char ch;
                if (tile.getType() == TileType.FLOOR && tile.hasItems()) {
                    ch = '!';                  // items here
                } else {
                    ch = switch (tile.getType()) {
                        case WALL  -> '#';
                        case FLOOR -> '.';
                        case SPAWN -> 'S';
                        case BOSS  -> 'B';
                        case DOOR  -> '+';
                    };
                }
                out.append(ch);
            }
            out.append('\n');
        }
        System.out.print(out);
    }
}