import org.example.entities.*;
import org.example.entities.Character;
import org.example.map.*;
import org.example.service.GameLoop;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static org.testng.Assert.*;

public class GameLoopTest {
    private Dungeon dungeon;
    private Player hero;
    private GameLoop gameLoop;
    private Random random;

    @BeforeMethod
    void setUp() {
        dungeon = new Dungeon(5, 5);
        hero = new Player("Hero", 30, 5, new Position(0, 0));
        gameLoop = new GameLoop(dungeon, hero);
        }

    @Test
    void testPlayerMoveIntoWalkableTile() {
        Position start = hero.getPosition();
        Position newPos = start.translate(1, 0);
        Tile targetTile = dungeon.getTile(newPos);

        dungeon.getTile(new Position(1, 0)).setType(TileType.FLOOR);
        assertTrue(targetTile.isWalkable(), "Target tile must be walkable for this test");

        gameLoop.movePlayer("d");
        assertEquals(newPos, hero.getPosition());
    }

    @Test
    void testPlayerCannotMoveIntoWall() {
        Position wallPos = new Position(1, 0);
        dungeon.getTile(wallPos).setType(TileType.WALL);

        Position start = hero.getPosition();
        gameLoop.movePlayer("d");

        assertEquals(start, hero.getPosition(), "Player should not move into a wall");
    }

    @Test
    void testPlayerPicksUpItem() {
        Item potion = new Item("Potion") {
            @Override
            public void use(Character character) {}
        };
        Position itemPos = new Position(1, 0);
        dungeon.getTile(itemPos).setType(TileType.FLOOR);
        dungeon.getTile(itemPos).setItem(potion);

        gameLoop.movePlayer("d");
        assertTrue(hero.getInventory().contains(potion));
        assertNull(dungeon.getTile(itemPos).getItem());
    }

    @Test
    void testPlayerWinsGame() {
        Position dragonPos = new Position(1, 0);
        dungeon.getTile(dragonPos).setType(TileType.FLOOR);
        Enemy dragon = new Enemy("Dragon", 1, 0, dragonPos) {
            @Override public String getType() { return "Dragon"; }
        };
        dungeon.getTile(dragonPos).setEnemy(dragon);

        gameLoop.movePlayer("d");

        assertTrue(gameLoop.hasVictory(), "GameLoop should mark victory");
    }

}