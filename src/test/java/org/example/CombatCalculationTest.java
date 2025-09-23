package org.example;

import org.example.entities.Boss;
import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.map.Tile;
import org.example.map.TileEnum;
import org.example.service.Combat;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CombatCalculationTest {

    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    @DisplayName("checks so skeleton.Attack() removes correct health from player")
    void enemyAttackReducesPlayerHealth() {
        Player player = new Player("Magnus", 100, 100, 100);
        Enemy skeleton = new Enemy("Skeleton", 50, 15, 0, 0);

        skeleton.Attack(player);

        assertEquals(85, player.getHealth());
    }

    @Test
    @DisplayName("checks so boss.useSpecialMove() deals extra 10 damage because its a special move")
    void bossSpecialMoveDealsExtraDamage() {
        Player player = new Player("Magnus", 100, 100, 100);
        Boss dragon = new Boss("Elder Dragon", 200, 25, 0, 0, "Flaming Breath");

        dragon.useSpecialMove(player);

        assertEquals(100 - 35, player.getHealth());
    }

    @Test
    @DisplayName("checks so Player doesn't take damage when enemy is killed first. And sets tile to Empty")
    void fightNonBossEnemyDiesImmediately() {
        Player player = new Player("Magnus", 100, 100, 100);
        Enemy weakEnemy = new Enemy("Skeleton", 10, 5, 0, 0);
        Tile tile = new Tile(TileEnum.ENEMY, weakEnemy, null);

        String simulatedInput = "attack\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Combat.Fight(player, weakEnemy, tile);

        assertEquals(TileEnum.EMPTY, tile.getType());
        assertEquals(100, player.getHealth());
    }
}

