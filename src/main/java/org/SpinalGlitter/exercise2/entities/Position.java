package org.SpinalGlitter.exercise2.entities;

public record Position(int x, int y) {
    public Position getAdjacent(int dx, int dy) {
        return new Position(this.x + dx, this.y + dy); } }