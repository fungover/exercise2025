package org.example.game;

import org.example.entities.HealingMilk;
import org.example.entities.Manifesto;
import org.example.entities.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class GameUseItemTest {

    static class BuildTestableGame extends Game {
        boolean exitCalled = false;
        int exitCode = -1;
        @Override
        protected void exit(int code) {
            exitCalled = true;
            exitCode = code;
            // Do not call System.exit when we use Manifesto
        }
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private PrintStream captureOut;

    @BeforeEach
    void setUpStreams() {
        originalOut = System.out;
        outContent.reset();
        captureOut = new PrintStream(outContent);
        System.setOut(captureOut);
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        if (captureOut != null) {
            captureOut.flush();
            captureOut.close();
        }
    }


     //Build a Game instance and inject a custom Player so we can control inventory and health.

    private static BuildTestableGame gameWithPlayer(Player player) throws Exception {
        BuildTestableGame game = new BuildTestableGame();
        Field f = Game.class.getDeclaredField("player");
        f.setAccessible(true);
        f.set(game, player);
        return game;
    }

    private static void invokeUseItem(Game game, String name) throws Exception {
        Method m = Game.class.getDeclaredMethod("useItem", String.class);
        m.setAccessible(true);
        m.invoke(game, name);
    }

    @Test
    @DisplayName("Using Healing Milk heals the player and removes the item from inventory")
    void useHealingMilk() throws Exception {
        Player player = new Player("Tester", 100, 0, 0);
        // simulate damage so healing has an effect
        player.takeDamage(30); // health = 70
        var milk = new HealingMilk(0, 0);
        player.addItem(milk);

        Game game = gameWithPlayer(player);

        invokeUseItem(game, "Healing Milk");

        // healed by 20 but not above max (70 -> 90)
        assertEquals(90, player.getHealth());
        // item removed from inventory
        assertFalse(player.getInventory().contains(milk));
        // console output should mention drinking milk and current health
        String out = outContent.toString();
        assertTrue(out.contains("You drink the Healing Milk"));
        assertTrue(out.contains("Your current health:"));
    }

    @Test
    @DisplayName("Using unknown item name prints a helpful message and changes nothing")
    void useUnknownItem() throws Exception {
        Player player = new Player("Tester", 100, 0, 0);
        int beforeHealth = player.getHealth();

        Game game = gameWithPlayer(player);

        invokeUseItem(game, "Nonexistent");

        // no change
        assertEquals(beforeHealth, player.getHealth());
        assertTrue(player.getInventory().isEmpty());
        String out = outContent.toString();
        assertTrue(out.contains("You don't have an item called 'Nonexistent'."));
    }

    @Test
    @DisplayName("Using Manifesto prints win text and attempts to exit (System.exit)—we only assert output")
    void useManifestoPrintsWinMessage() throws Exception {
        Player player = new Player("Tester", 100, 0, 0);
        var manifesto = new Manifesto(0, 0);
        player.addItem(manifesto);

        BuildTestableGame game = gameWithPlayer(player);

        invokeUseItem(game, "Manifesto");

        String out = outContent.toString();
        assertTrue(out.contains(manifesto.getWinMessage()));
        assertTrue(out.contains("Congratulations, Tester! You win!"));
        // Item should be removed
        assertFalse(player.getInventory().contains(manifesto));
        // Exit should have been requested with code 0, but not actually executed
        assertTrue(game.exitCalled);
        assertEquals(0, game.exitCode);
    }
}
