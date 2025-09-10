package dev.ronja.dungeon.service;

import dev.ronja.dungeon.map.Dungeon;
import dev.ronja.dungeon.map.Position;

public class MovementService {
    private final dev.ronja.dungeon.map.Dungeon dungeon;

    public MovementService(dev.ronja.dungeon.map.Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public dev.ronja.dungeon.map.Position tryMove(dev.ronja.dungeon.map.Position from, int dx, int dy) {
        var target = from.add(dx, dy);
        return dungeon.isWalkable(target) ? target : from;
    }

}

