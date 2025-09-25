package org.example.service.movement;

import org.example.entities.characters.Player;
import org.example.map.DungeonMap;
import org.example.utils.Direction;

import static org.example.utils.Guard.notNull;

public final class MovementService {

    public enum MoveResult {MOVED, BLOCKED_WALL, BLOCKED_OUT_OF_BOUNDS, BLOCKED_NON_WALKABLE}

    public MoveResult tryMove(Player player, DungeonMap map, Direction dir) {
        notNull(player, "player");
        notNull(map, "map");
        notNull(dir, "dir");
        int nx = player.getX() + dir.dx;
        int ny = player.getY() + dir.dy;

        if (!map.isInside(nx, ny)) return MoveResult.BLOCKED_OUT_OF_BOUNDS;
        var type = map.tileAt(nx, ny).getType();
        // Walkable allowlist: FLOOR, SPAWN, BOSS
        switch (type) {
            case WALL -> {
                return MoveResult.BLOCKED_WALL;
            }
            case FLOOR, SPAWN, BOSS -> {
                player.moveTo(nx, ny);
                return MoveResult.MOVED;

            }
            default -> {
                return MoveResult.BLOCKED_NON_WALKABLE;
            }

        }
    }
}
