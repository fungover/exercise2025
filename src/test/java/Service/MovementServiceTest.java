package Service;

import Entities.*;
import Map.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class MovementServiceTest {

    DungeonMap map;
    MovementService movement;
    Player player;

    @BeforeEach
    void setup() {
        map = new DungeonMap(5, 5);
        map.generateBasicLayout();
        player = new Player("Hero", 30, 5, 1, 1);
        movement = new MovementService(map);
    }

    @Test
    void movesOntoFloor() {
        int startX = player.getX();
        int startY = player.getY();

        movement.move(player, 1, 0); // east

        assertEquals(startX + 1, player.getX());
        assertEquals(startY, player.getY());
    }

    @Test
    void cannotMoveIntoWall() {

        player.setPos(1, 1);
        movement.move(player, -1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void picksUpItemWhenEnteringTile() {
        Tile t = map.tile(2, 1);
        t.setItem(new Item("Small Potion", ItemType.POTION, 10));

        int oldHp = player.getHealth();
        movement.move(player, 1, 0);

        assertNull(t.getItem(), "Tile item should be cleared after pickup");

        player.useFirstPotion();
        assertEquals(oldHp + 10, player.getHealth());
    }
}
