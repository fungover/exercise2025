package org.example.entities;

public record Position (int x, int y) {

    public Position translate(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }
}