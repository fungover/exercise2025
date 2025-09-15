package org.example.service;

public enum MovementEnum {
    UP(0, -1), DOWN(0,1), LEFT(-1, 0), RIGHT(1, 0);

    public final int newX, newY;

    MovementEnum(int newX, int newY) {
        this.newX = newX;
        this.newY = newY;
    }

    public static MovementEnum movementFromString(String s) {
        return switch (s.toLowerCase()) {
            case "up" -> UP;
            case "down" -> DOWN;
            case "left" -> LEFT;
            case "right" -> RIGHT;
            default -> null;
        };
    }
}
