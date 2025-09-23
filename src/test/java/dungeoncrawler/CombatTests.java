package dungeoncrawler;

import dungeoncrawler.enteties.Enemy;
import dungeoncrawler.enteties.Giant;
import dungeoncrawler.enteties.Player;
import dungeoncrawler.enteties.Troll;
import dungeoncrawler.game.Game;
import dungeoncrawler.map.Dungeon;
import dungeoncrawler.service.Combat;
import dungeoncrawler.service.GameLogic;
import dungeoncrawler.service.Movement;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CombatTests {
    @Test
    void testAttackTrollHit(){
        int[] position = {3, 2};
        Player player = new Player("Name", 10,position);
        Enemy enemy = new Troll(position);
        Combat combat = new Combat(new GameLogic(new Dungeon(), player, new Game()), new Movement());
        int result = combat.fight(enemy, 7);
        assertEquals(4, result);
        assertEquals(10, player.getHp());
    }
    @Test
    void testAttackTrollMiss(){
        int[] position = {3, 2};
        Player player = new Player("Name", 20,position);
        Enemy enemy = new Troll(position);
        Combat combat = new Combat(new GameLogic(new Dungeon(), player, new Game()), new Movement());
        int result = combat.fight(enemy, 3);
        assertEquals(4, result);
        assertEquals(20, player.getHp());
    }
    @Test
    void testAttackGiantHit(){
        int[] position = {3, 2};
        Player player = new Player("Name", 10,position);
        Enemy enemy = new Giant(position);
        Combat combat = new Combat(new GameLogic(new Dungeon(), player, new Game()), new Movement());
        int result = combat.fight(enemy, 7);
        assertEquals(4, result);
        assertEquals(10, player.getHp());
    }
    @Test
    void testAttackGiantMiss(){
        int[] position = {3, 2};
        Player player = new Player("Name", 20,position);
        Enemy enemy = new Giant(position);
        Combat combat = new Combat(new GameLogic(new Dungeon(), player, new Game()), new Movement());
        int result = combat.fight(enemy, 3);
        assertEquals(4, result);
        assertEquals(20, player.getHp());
    }
    @Test
    void testRunAway(){
        int[] position = {3, 2};
        int[] prevPosition = {3, 1};
        Player player = new Player("Name", 20,position);
        player.setPreviousPosition(prevPosition);
        //Enemy enemy = new Giant(position);
        Combat combat = new Combat(new GameLogic(new Dungeon(), player, new Game()), new Movement());
        int result = combat.run(player);
        assertEquals(2, result);
        assertEquals(prevPosition[0], player.getPosition()[0]);
        assertEquals(prevPosition[1], player.getPosition()[1]);
    }
    @Test
    void testHideSucceed(){
        int number = 9;
        int[] position = {3, 2};
        int[] prevPosition = {3, 1};
        Player player = new Player("Name", 20,position);
        player.setPreviousPosition(prevPosition);
        //Enemy enemy = new Giant(position);
        Combat combat = new Combat(new GameLogic(new Dungeon(), player, new Game()), new Movement());
        /*Tile tile = new Tile("Giant");
        tile.setPosition(position);*/
        int result = combat.hide(player, number);
        assertEquals(2, result);
    }
    @Test
    void testHideFail(){
        int number = 3;
        int[] position = {3, 2};
        int[] prevPosition = {3, 1};
        Player player = new Player("Name", 20,position);
        player.setPreviousPosition(prevPosition);
        //Enemy enemy = new Giant(position);
        Combat combat = new Combat(new GameLogic(new Dungeon(), player, new Game()), new Movement());
        /*Tile tile = new Tile("Giant");
        tile.setPosition(position);*/
        int result = combat.hide(player, number);
        assertEquals(4, result);
    }

}
