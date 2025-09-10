package dev.ronja.dungeon.game;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CommandParser {
    @FunctionalInterface
    public interface Command extends Consumer<String[]> {} // args-> run

    private final Map<String, Command> commands = new HashMap<>();
    private final Map<String, String> alias = new HashMap<>();
    private final Runnable onUnknown;

    public CommandParser(Runnable onUnknown) {
        this.onUnknown = (onUnknown != null) ? onUnknown : () -> System.out.println("Unknown command.");
    }

    public CommandParser add(String name, Command cmd, String... aliases) {
        commands.put(name, cmd);
        for (var a : aliases) alias.put(a, name);
        return this;
    }

}
