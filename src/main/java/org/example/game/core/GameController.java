package org.example.game.core;

import java.util.Scanner;

import org.example.game.cli.ConsoleMapPrinter;
import org.example.game.cli.InputParser;
import org.example.service.movement.MovementService;

public final class GameController {
    private final MovementService mover = new MovementService();

    public void run(GameContext ctx, Scanner in) {
        // Initial render
        ConsoleMapPrinter.print(ctx.map(), ctx.player().getX(), ctx.player().getY());

        while (true) {
            System.out.print("> ");
            String line = in.nextLine();

            var parsed = InputParser.parse(line);
            switch (parsed.type) {
                case QUIT -> { System.out.println("Goodbye!"); return; }
                case UNKNOWN -> {
                    System.out.println("Unknown command. Try WASD or 'north/south/east/west'.");
                    ConsoleMapPrinter.print(ctx.map(), ctx.player().getX(), ctx.player().getY());
                }
                case MOVE -> {
                    var result = mover.tryMove(ctx.player(), ctx.map(), parsed.direction);
                    ConsoleMapPrinter.print(ctx.map(), ctx.player().getX(), ctx.player().getY());
                    switch (result) {
                        case MOVED -> System.out.println("You move " + parsed.direction.name().toLowerCase() + ".");
                        case BLOCKED_WALL -> System.out.println("A wall blocks your way.");
                        case BLOCKED_OUT_OF_BOUNDS -> System.out.println("You can't go further.");
                    }
                }
            }
        }
    }
}
