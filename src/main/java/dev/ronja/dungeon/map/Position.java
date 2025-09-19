package dev.ronja.dungeon.map;

/**
 * A simple and immutable coordinate in my grid system.
 * Data is safe and sound, ready to be used in hash-maps & sets
 **/

public record Position(int x, int y) {
    public Position add(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }
}
