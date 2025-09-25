package org.example.utils;

import org.example.map.Room;

public final class GridMath {
    private GridMath() {}

    /** Manhattan distance in grid steps (no diagonals). */
    public static int manhattan(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /** Clamp value into [min, max]. */
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    /**
     * Compute a top-left tile for placing a footprint of (footprintWidth × footprintHeight)
     * roughly centered inside the room, fully contained.
     */
    public static Position topLeftCentered(Room room, int footprintWidth, int footprintHeight) {
        int centerX = room.centerX();
        int centerY = room.centerY();

        int maxTopLeftX = room.right() - (footprintWidth - 1);
        int maxTopLeftY = room.bottom() - (footprintHeight - 1);

        int topLeftX = clamp(centerX - footprintWidth / 2, room.left(), maxTopLeftX);
        int topLeftY = clamp(centerY - footprintHeight / 2, room.top(), maxTopLeftY);

        return new Position(topLeftX, topLeftY);
    }
}
