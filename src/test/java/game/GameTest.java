package game;

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
    public void

}
