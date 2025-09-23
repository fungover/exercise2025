package org.example;

import org.example.service.MovementEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovementLogicTest {

    @Test
    @DisplayName("method movementFromString_validInput will recieve the correnct enum from input")
    void movementFromString_validInput() {
        assertEquals(MovementEnum.UP, MovementEnum.movementFromString("UP"));
        assertEquals(MovementEnum.DOWN, MovementEnum.movementFromString("DOWN"));
        assertEquals(MovementEnum.LEFT, MovementEnum.movementFromString("LEFT"));
        assertEquals(MovementEnum.RIGHT, MovementEnum.movementFromString("RIGHT"));
    }

    @Test
    @DisplayName("method movementFromString_invalidInput will return null if its an invalid entry")
    void movementFromString_invalidInput() {
        assertNull(MovementEnum.movementFromString("forward"));
        assertNull(MovementEnum.movementFromString(""));
        assertNull(MovementEnum.movementFromString("go right"));
    }

    @Test
    @DisplayName("method movementEnums checks that enum is correct with X & Y values for vector")
    void movementEnums_validReturns() {
        assertAll(
                () -> assertEquals(0, MovementEnum.UP.newX),
                () -> assertEquals(-1, MovementEnum.UP.newY),

                () -> assertEquals(0, MovementEnum.DOWN.newX),
                () -> assertEquals(1, MovementEnum.DOWN.newY),

                () -> assertEquals(-1, MovementEnum.LEFT.newX),
                () -> assertEquals(0, MovementEnum.LEFT.newY),

                () -> assertEquals(1, MovementEnum.RIGHT.newX),
                () -> assertEquals(0, MovementEnum.RIGHT.newY)
        );
    }
}
