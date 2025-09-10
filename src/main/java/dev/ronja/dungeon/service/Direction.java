package dev.ronja.dungeon.service;
/** This is for the moving logics  **/
public enum Direction {
    N(0, -1), S(0, 1), W(-1, 0), E(1, 0);

    public final int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
