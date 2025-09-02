package org.example.entities;

import java.util.Objects;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position moveNorth() {
        return new Position(x, y - 1);
    }
    public Position moveSouth() {
        return new Position(x, y + 1);
    }
    public Position moveEast() {
        return new Position(x + 1, y);
    }
    public Position moveWest() {
        return new Position(x - 1, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position(" + x + ", " + y + ")";
    }
}
