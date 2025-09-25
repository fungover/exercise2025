package org.game.world;

import org.game.world.Tile;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MapGenerationBasicTest {

    @Test
    void canGenerateDungeon() {
        Dungeon dungeon = new Dungeon(6, 6);

        // Inside tiles access should not be null
        Tile t = dungeon.getTile(1, 1);
        assertThat(t).isNotNull();

        // Outside tiles access should be null
        assertThat(dungeon.getTile(-1, 0)).isNull();
        assertThat(dungeon.getTile(6, 0)).isNull();
        assertThat(dungeon.getTile(0, -1)).isNull();
        assertThat(dungeon.getTile(0, 6)).isNull();
    }

    @Test
    void hasWalkableTiles() {
        Dungeon dungeon = new Dungeon(8, 8);

        int walkableCount = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (dungeon.isWalkable(x, y)) {
                    walkableCount++;
                }
            }
        }

        assertThat(walkableCount).isGreaterThan(0);
    }

    @Test
    void cantWalkOutsideMap() {
        Dungeon dungeon = new Dungeon(4, 4);

        assertThat(dungeon.isWalkable(-1, 0)).isFalse();
        assertThat(dungeon.isWalkable(0, -1)).isFalse();
        assertThat(dungeon.isWalkable(4, 0)).isFalse();
        assertThat(dungeon.isWalkable(0, 4)).isFalse();
    }
}