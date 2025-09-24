package org.example;

import entities.Player;
import map.DungeonMap;
import map.TileType;
import org.junit.jupiter.api.Test;
import service.Movement;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

class MovementTest {

    @Test
    void move_respectsWallsAndBounds_andUsesDeltas() {
        DungeonMap map = new DungeonMap(10, 10);
        map.getTile(1,1).setType(TileType.FLOOR);
        map.getTile(1,2).setType(TileType.FLOOR);

        Player p = new Player();
        p.setPosition(new Position(1,1));

        Movement mv = new Movement(p, map);
        mv.move(-1, 0);
        assertEquals(1, p.getPosition().getX());
        assertEquals(1, p.getPosition().getY());

        mv.move(0, 1);
        assertEquals(1, p.getPosition().getX());
        assertEquals(2, p.getPosition().getY());

        mv.move(0, 999);
        assertEquals(1, p.getPosition().getX());
        assertEquals(2, p.getPosition().getY());
    }
}