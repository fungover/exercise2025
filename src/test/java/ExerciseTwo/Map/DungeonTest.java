package ExerciseTwo.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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
    @ParameterizedTest
    @MethodSource("Dungeons")
    void throwsIllegalArgumentExceptionWhenDungeonMapDosenContainExpectedSymbol(String[][] dungeonMap) {

        assertThrows(IllegalArgumentException.class, () -> new Dungeon(dungeonMap) {
            @Override
            public void description() {

            }
        });
    }

    static Stream<String[][]> Dungeons(){
        return Stream.of(
                new String[][]{
                        {"#", "#", "#"},{" ", " ", "#"},{"#", "D", "#"}
                },
                new String[][]{
                        {"#", "#", "#"},{"#", " ", " "},{"#", "D", "#"}
                },
                new String[][]{
                        {"#", " ", "#"},{"#", " ", "#"},{"#", "T", "#"}
                }
        );
    }

}