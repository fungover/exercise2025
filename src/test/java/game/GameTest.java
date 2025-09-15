package game;

import entities.Enemy;
import entities.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
