package org.example.map;

import org.example.entities.Player;
import org.example.utils.Enemies;
import org.example.utils.ItemsOnFloor;
import org.example.utils.RandomGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DungeonTest {
    private final Dungeon dungeon = new Dungeon(
            new Player("AngryHippo", 20),
            new Enemies(),
            new ItemsOnFloor());
    private final RandomGenerator mockRand = mock(RandomGenerator.class);

    @Test
    public void testDungeonGeneration() {
        when(mockRand.generateNumber(1, 10)).thenReturn(dungeon.getColumns());

        assertThat(dungeon.getRows()).isEqualTo(7);
        assertThat(dungeon.getColumns()).isEqualTo(mockRand.generateNumber(1, 10));
    }
}
