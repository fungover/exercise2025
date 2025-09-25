package org.example.service.movement;

import org.example.entities.characters.Warrior;
import org.example.map.DungeonMap;
import org.example.map.TileType;
import org.example.utils.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class MovementServiceTest {

    @Test
    void move_into_floor_returns_MOVED_and_updates_position() {
        final DungeonMap dungeonMap = new DungeonMap(3, 3);
        dungeonMap.tileAt(1, 1).setType(TileType.FLOOR);
        dungeonMap.tileAt(1, 0).setType(TileType.FLOOR);

        final Warrior player = new Warrior("Test", 1, 1);
        final MovementService movementService = new MovementService();

        final MovementService.MoveResult moveResult =
                movementService.tryMove(player, dungeonMap, Direction.NORTH);

        assertEquals(MovementService.MoveResult.MOVED, moveResult, "Expected to move onto FLOOR.");
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    void move_into_wall_returns_BLOCKED_WALL_and_keeps_position() {
        final DungeonMap dungeonMap = new DungeonMap(3, 3);
        dungeonMap.tileAt(1, 1).setType(TileType.FLOOR);
        dungeonMap.tileAt(1, 0).setType(TileType.WALL);

        final Warrior player = new Warrior("Test", 1, 1);
        final MovementService movementService = new MovementService();

        final MovementService.MoveResult moveResult =
                movementService.tryMove(player, dungeonMap, Direction.NORTH);

        assertEquals(MovementService.MoveResult.BLOCKED_WALL, moveResult, "Wall should block movement.");
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void move_out_of_bounds_returns_BLOCKED_OUT_OF_BOUNDS_and_keeps_position() {
        final DungeonMap dungeonMap = new DungeonMap(2, 2);
        dungeonMap.tileAt(0, 0).setType(TileType.FLOOR);

        final Warrior player = new Warrior("Test", 0, 0);
        final MovementService movementService = new MovementService();

        final MovementService.MoveResult moveResult =
                movementService.tryMove(player, dungeonMap, Direction.NORTH);

        assertEquals(MovementService.MoveResult.BLOCKED_OUT_OF_BOUNDS, moveResult, "Edge of map should block movement.");
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }
}
