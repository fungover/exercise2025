package org.example.service;

import org.example.entities.Player;
import org.example.map.Dungeon;
import org.example.map.TileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovementServiceTest {

    @Test
    void player_cannot_move_into_wall() {
        Dungeon dungeon = new Dungeon(1);
        Player player = new Player("Player");
        MovementService movement = new MovementService();

        // Startposition är (1,1) så west och north är vägg
        boolean moved = movement.movePlayer(player, dungeon, "west");
        assertFalse(moved);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());

        moved = movement.movePlayer(player, dungeon, "north");
        assertFalse(moved);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void player_can_move_into_walkable_space() {
        Dungeon dungeon = new Dungeon(1);
        Player player = new Player("Player");
        MovementService movement = new MovementService();

        // Startposition är (1,1) så går att gå i riktningarna east och south
        boolean moved = movement.movePlayer(player, dungeon, "east");
        assertTrue(moved);
        assertEquals(2, player.getX());
        assertEquals(1, player.getY());

        moved = movement.movePlayer(player, dungeon, "south");
        assertTrue(moved);
        assertEquals(2, player.getX());
        assertEquals(2, player.getY());
    }

    @Test
    void player_reaches_exit_tile() {
        Dungeon dungeon = new Dungeon(1);
        Player player = new Player("Player");

        // Flytta spelaren direkt till EXIT
        player.moveTo(8, 8); // eftersom EXIT_X = WIDTH - 2, EXIT_Y = HEIGHT - 2
        assertEquals(TileType.EXIT, dungeon.get(8, 8).getType(),
                "Player should stand on the exit tile at (8,8)");
    }

    @Test
    void inBounds_checks_coordinates() {
        Dungeon dungeon = new Dungeon(1);

        // In-bounds
        assertTrue(dungeon.inBounds(0, 0));
        assertTrue(dungeon.inBounds(9, 9));

        // Out-of-bounds
        assertFalse(dungeon.inBounds(-1, 0));
        assertFalse(dungeon.inBounds(0, -1));
        assertFalse(dungeon.inBounds(10, 5));
        assertFalse(dungeon.inBounds(5, 10));
    }
}
