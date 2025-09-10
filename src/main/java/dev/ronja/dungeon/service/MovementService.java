package dev.ronja.dungeon.service;

import dev.ronja.dungeon.map.Dungeon;
import dev.ronja.dungeon.map.Position;

public class MovementService {
    private final Dungeon dungeon;

    public MovementService(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Position tryMove(Position from, int dx, int dy) {
        var target = from.add(dx, dy);
        return dungeon.isWalkable(target) ? target : from;
    }

    public boolean canMove(Position from, Direction dir) {
        var target = from.add(dir.dx, dir.dy);
        return dungeon.isWalkable(target);
    }

}

