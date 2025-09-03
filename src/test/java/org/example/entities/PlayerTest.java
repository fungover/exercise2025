package org.example.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void player_has_name_and_hp_and_start_position() {
        Player player = new Player("Player");

        assertEquals("Player", player.getName());
        assertEquals(100, player.getHp());
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void player_hp_can_be_changed () {
        Player player = new Player("Player");

        player.setHp(50);
        assertEquals(50, player.getHp());

        player.setHp(player.getHp() + 50);
        assertEquals(100, player.getHp());

        player.setHp(0);
        assertEquals(0, player.getHp());
    }

    @Test
    void player_levels_up_when_gaining_enough_xp() {
        Player player = new Player("Player");

        player.gainXp(100);

        assertEquals(2, player.getLevel(), "Player should level up to level 2");
        assertEquals(120, player.getMaxHp(), "Max HP should increase on level up");
        assertEquals(120, player.getHp(), "HP should be restored to new max HP");
    }
}
