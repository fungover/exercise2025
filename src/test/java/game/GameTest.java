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
}
