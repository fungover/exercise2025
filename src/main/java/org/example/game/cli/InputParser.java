package org.example.game.cli;

import org.example.utils.Direction;

public final class InputParser {
    public enum Type { MOVE, QUIT, UNKNOWN }

    public static final class Parsed {
        public final Type type;
        public final Direction direction; // only for MOVE

        public Parsed(Type type, Direction direction) {
            this.type = type; this.direction = direction;
        }

        public static Parsed move(Direction d) {
            return new Parsed(Type.MOVE, d);
        }

        public static Parsed quit() {
            return new Parsed(Type.QUIT, null);
        }

        public static Parsed unknown() {
            return new Parsed(Type.UNKNOWN, null);
        }
    }

    private InputParser() {}

    public static Parsed parse(String line) {
        if (line == null || line.isBlank()) return Parsed.unknown();
        String input = line.trim().toLowerCase();
        if (input.equals("quit") || input.equals("q") || input.equals("exit")) return Parsed.quit();
        if (input.startsWith("move ")) input = input.substring(5);

        // WASD single-letter
        if (input.length() == 1) {
            return switch (input) {
                case "w" -> Parsed.move(Direction.NORTH);
                case "a" -> Parsed.move(Direction.WEST);
                case "s" -> Parsed.move(Direction.SOUTH);
                case "d" -> Parsed.move(Direction.EAST);
                default  -> Parsed.unknown();
            };
        }
        // full words
        var dir = Direction.fromInput(input);
        return (dir != null) ? Parsed.move(dir) : Parsed.unknown();
    }
}
