package org.SpinalGlitter.exercise2.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTest {

    @Test
    void testGetters() {
        Position pos = new Position(3, 4);

        assertEquals(3, pos.x());
        assertEquals(4, pos.y());
    }

    @Test
    void testGetAdjacentPositiveDelta() {
        Position pos = new Position(2, 2);
        Position adjacent = pos.getAdjacent(1, 1);

        assertEquals(new Position(3, 3), adjacent);
    }

    @Test
    void testGetAdjacentNegativeDelta() {
        Position pos = new Position(5, 5);
        Position adjacent = pos.getAdjacent(-2, -3);

        assertEquals(new Position(3, 2), adjacent);
    }

    @Test
    void testGetAdjacentZeroDelta() {
        Position pos = new Position(7, 8);
        Position adjacent = pos.getAdjacent(0, 0);

        assertEquals(pos, adjacent);
    }
}