package ExerciseTwo.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DungeonTest {

    @Test
    void throwsIllegalArgumentExceptionWhenEmptyMap() {
        String[][] map = {};

        assertThrows(IllegalArgumentException.class, () -> new Dungeon(map) {
            @Override
            public void description() {

            }
        });

    }
}