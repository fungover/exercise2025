package org.example.service;

import org.example.entities.Player;
import org.example.map.Dungeon;
import org.example.utils.Enemies;
import org.example.utils.ItemsOnFloor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MovementTest {
    private final GameLogic gameLogic = new GameLogic();
    private final Player player = new Player("HungryHippo", 50);
    private final Enemies enemies = new Enemies();
    private final ItemsOnFloor itemsOnFloor = new ItemsOnFloor();
    private final Dungeon dungeon = new Dungeon(player, enemies, itemsOnFloor);

    @Test
    public void testMovingUp() {
        player.setPosition(3, 3);
        gameLogic.moveInput(dungeon, player, "U");
        assertThat(player.getX()).isEqualTo(3);
        assertThat(player.getY()).isEqualTo(2);
    }

    @Test
    public void testMovingDown() {
        player.setPosition(3, 3);
        gameLogic.moveInput(dungeon, player, "D");
        assertThat(player.getX()).isEqualTo(3);
        assertThat(player.getY()).isEqualTo(4);
    }

    @Test
    public void testMovingLeft() {
        player.setPosition(3, 3);
        gameLogic.moveInput(dungeon, player, "L");
        assertThat(player.getX()).isEqualTo(2);
        assertThat(player.getY()).isEqualTo(3);
    }

    @Test
    public void testMovingRight() {
        player.setPosition(3, 3);
        gameLogic.moveInput(dungeon, player, "R");
        assertThat(player.getX()).isEqualTo(4);
        assertThat(player.getY()).isEqualTo(3);
    }
}
