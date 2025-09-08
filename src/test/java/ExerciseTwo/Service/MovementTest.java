package ExerciseTwo.Service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovementTest {

    String[][] map = {{"#", "#", "#"},{"#","@","#"},{"#"," ","#"},{"#","#","#"}};
    Position position = new Position(map);

    @Test
    void checkThatPlayerMoves() {
        Movement movement = new Movement(position);
        assertEquals("path", movement.checkMovement(map, "s"));
    }
}