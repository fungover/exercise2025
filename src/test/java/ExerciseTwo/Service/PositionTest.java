package ExerciseTwo.Service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {


    String[][] map = {{"#", "#", "#"},{"#","@","#"},{"#"," ","#"},{"#","#","#"}};

    @Test
    void checkThatPlayerPositionCanBeDetected() {

        Position position = new Position(map);

        assertEquals(1, position.getRow());
        assertEquals(1, position.getCol());
    }

}