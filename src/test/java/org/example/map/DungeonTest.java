package org.example.map;

import org.example.entities.Player;
import org.example.utils.Enemies;
import org.example.utils.ItemsOnFloor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DungeonTest {
    private final Dungeon dungeon = new Dungeon(
            new Player("AngryHippo", 20),
            new Enemies(),
            new ItemsOnFloor());

    @Test
    public void testDungeonGeneration() {
        assertThat(dungeon.getRows()).isEqualTo(7);
        assertThat(dungeon.getColumns())
                .isGreaterThanOrEqualTo(10)
                .isLessThanOrEqualTo(20);
    }
}
