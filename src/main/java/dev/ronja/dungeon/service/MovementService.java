package dev.ronja.dungeon.service;

import dev.ronja.dungeon.map.Dungeon;
import dev.ronja.dungeon.map.Position;

/**
 * The logic for calculating movements
 **/
public class MovementService {
    private final Dungeon dungeon;

    public MovementService(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Position tryMove(Position from, int dx, int dy) {
        var target = from.add(dx, dy);
        return dungeon.isWalkable(target) ? target : from;
    }

}

