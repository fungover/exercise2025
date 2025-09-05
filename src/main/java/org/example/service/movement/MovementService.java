package org.example.service.movement;

import org.example.entities.characters.Player;
import org.example.map.DungeonMap;
import org.example.map.TileType;
import org.example.utils.Direction;

public final class MovementService {
    public enum MoveResult { MOVED, BLOCKED_WALL, BLOCKED_OUT_OF_BOUNDS }

    public MoveResult tryMove(Player player, DungeonMap map, Direction dir) {
        int nx = player.getX() + dir.dx;
        int ny = player.getY() + dir.dy;

        if (!map.isInside(nx, ny)) return MoveResult.BLOCKED_OUT_OF_BOUNDS;
        var tile = map.tileAt(nx, ny);
        if (tile.getType() == TileType.WALL) return MoveResult.BLOCKED_WALL;

        // Walkable: FLOOR, SPAWN, BOSS
        player.moveTo(nx, ny);
        return MoveResult.MOVED;
    }
}
