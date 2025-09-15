package game;

import entities.Enemy;
import entities.Player;
import entities.Potion;
import map.Dungeon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GameTest {

    @Test
    public void testHandleInput_MoveRight() {
        Game game = new Game();
        int oldX = game.player.getX();
        game.handleInput("d");
        assertEquals(oldX + 1, game.player.getX());
    }

    @Test
    public void testHandleInput_MoveDown() {
        Game game = new Game();
        int oldY = game.player.getY();
        game.handleInput("s");
        assertEquals(oldY + 1, game.player.getY());
    }

    @Test
    public void testPlayerDealsDamage() {
        Player player = new Player(0, 0, 100, 50);
        Enemy enemy = new Enemy(0, 0, 100, 30);

        enemy.setHealth(enemy.getHealth() - player.getDamage());

        assertEquals(50, enemy.getHealth());
    }

    @Test
    public void testEnemyDealsDamage() {
        Player player = new Player(0, 0, 100, 50);
        Enemy enemy = new Enemy(0, 0, 100, 30);

        player.setHealth(player.getHealth() - enemy.getDamage());

        assertEquals(70, player.getHealth());
    }

    @Test
    public void testPlayerPicksUpPotion(){
        Dungeon dungeon = new Dungeon(10,10);
        Player player = new Player(1, 1, 100, 50);
        Potion potion = new Potion("Potion", 20);

        dungeon.placeItem(2, 1, potion);
        player.move(1, 0, dungeon);

        if (dungeon.getItemAt(player.getX(), player.getY()) != null) {
            dungeon.getItemAt(player.getX(), player.getY()).use(player);
            dungeon.removeItemAt(player.getX(), player.getY());
        }

        assertEquals(120, player.getHealth(), "should now be 120");
        assertNull(dungeon.getItemAt(player.getX(), player.getY()));
    }

}
