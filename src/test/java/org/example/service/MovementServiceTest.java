package org.example.service;

import org.example.entities.Player;
import org.example.map.Dungeon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovementServiceTest {

    @Test
    void player_cannot_move_into_wall() {
        Dungeon dungeon = new Dungeon();
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
        Dungeon dungeon = new Dungeon();
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
}
