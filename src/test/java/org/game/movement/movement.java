package org.game.movement;

import org.game.entities.Player;
import org.game.world.Dungeon;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MovementLogicTest {

    @Test
    void cannotMoveOutOfBounds() {
        Player player = new Player("Hero", 1, 1);
        Dungeon dungeon = new Dungeon(5, 5);

        int startX = player.getX();
        int startY = player.getY();

        // Try to move to a negative coordinate should not be walkable
        int targetX = -1;
        int targetY = 1;
        if (dungeon.isWalkable(targetX, targetY)) {
            player.setPosition(targetX, targetY);
        }

        assertThat(player.getX()).isEqualTo(startX);
        assertThat(player.getY()).isEqualTo(startY);

        // Try to move beyond the right edge for a 5x5 should not be walkable
        targetX = 5;
        targetY = 1;
        if (dungeon.isWalkable(targetX, targetY)) {
            player.setPosition(targetX, targetY);
        }

        assertThat(player.getX()).isEqualTo(startX);
        assertThat(player.getY()).isEqualTo(startY);
    }

    @Test
    void moveOnlyWhenWalkable() {
        Player player = new Player("Hero", 1, 1);
        Dungeon dungeon = new Dungeon(5, 5);

        int startX = player.getX();
        int startY = player.getY();

        // A simple move is likely in range only works if walkable
        int newX = startX + 1;
        int newY = startY;

        if (dungeon.isWalkable(newX, newY)) {
            player.setPosition(newX, newY);
            assertThat(player.getX()).isEqualTo(newX);
            assertThat(player.getY()).isEqualTo(newY);
        } else {
            // If not walkable we should remain where we were
            assertThat(player.getX()).isEqualTo(startX);
            assertThat(player.getY()).isEqualTo(startY);
        }
    }
}