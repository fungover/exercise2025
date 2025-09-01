package org.example.game;

import org.example.map.DungeonMap;
import org.example.map.TileType;

/** Utility to print a DungeonMap as ASCII for quick preview/testing. */
public final class ConsoleMapPrinter {
    private ConsoleMapPrinter() {}

    public static void print(DungeonMap map) {
        StringBuilder output = new StringBuilder();
        for (int row = 0; row < map.height(); row++) {
            for (int column = 0; column < map.width(); column++) {
                TileType type = map.tileAt(column, row).getType();
                char ch = switch (type) {
                    case WALL  -> '#';
                    case FLOOR -> '.';
                    case SPAWN -> 'S';
                    case BOSS  -> 'B';
                    case DOOR  -> '+';
                };
                output.append(ch);
            }
            output.append('\n');
        }
        System.out.print(output);
    }
}
