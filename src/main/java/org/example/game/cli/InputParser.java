package org.example.game.cli;

import org.example.utils.Direction;

public final class InputParser {

    public enum Type { MOVE, INVENTORY, LOOT, TAKE, USE, HELP, MAP, QUIT, UNKNOWN }

    public record Parsed(Type type, Direction direction, Integer oneBasedIndex, boolean takeAllFlag) {

        public static Parsed move(Direction d) {
            return new Parsed(Type.MOVE, d, null, false);
        }

        public static Parsed inventory() {
            return new Parsed(Type.INVENTORY, null, null, false);
        }

        public static Parsed loot() {
            return new Parsed(Type.LOOT, null, null, false);
        }

        public static Parsed takeIndex(int idx) {
            return new Parsed(Type.TAKE, null, idx, false);
        }

        public static Parsed takeAll() {
            return new Parsed(Type.TAKE, null, null, true);
        }

        public static Parsed useIndex(int idx) {
            return new Parsed(Type.USE, null, idx, false);
        }

        public static Parsed help() {
            return new Parsed(Type.HELP, null, null, false);
        }

        public static Parsed map() {
            return new Parsed(Type.MAP, null, null, false);
        }

        public static Parsed quit() {
            return new Parsed(Type.QUIT, null, null, false);
        }

        public static Parsed unknown() {
            return new Parsed(Type.UNKNOWN, null, null, false);
        }
        }

    private InputParser() {}

    public static Parsed parse(String line) {
        if (line == null || line.isBlank()) return Parsed.unknown();

        String inputText = line.trim().toLowerCase();
        String[] parts = inputText.split("\\s+");
        String head = parts[0];

        switch (head) {
            case "q", "quit" -> { return Parsed.quit(); }
            case "h", "help" -> { return Parsed.help(); }
            case "m", "map"  -> { return Parsed.map(); }
            case "i", "inventory" -> { return Parsed.inventory(); }
            case "l", "loot" -> { return Parsed.loot(); }

            case "take" -> {
                if (parts.length < 2) return Parsed.unknown();
                String arg = parts[1];
                if ("all".equals(arg)) return Parsed.takeAll();
                Integer idx = parsePositiveInt(arg);
                return (idx != null) ? Parsed.takeIndex(idx) : Parsed.unknown();
            }

            case "use" -> {
                if (parts.length < 2) return Parsed.unknown();
                Integer idx = parsePositiveInt(parts[1]);
                return (idx != null) ? Parsed.useIndex(idx) : Parsed.unknown();
            }

            // Movement: only WASD or full words.
            default -> {
                if (head.length() == 1) {
                    return switch (head) {
                        case "w" -> Parsed.move(Direction.NORTH);
                        case "a" -> Parsed.move(Direction.WEST);
                        case "s" -> Parsed.move(Direction.SOUTH);
                        case "d" -> Parsed.move(Direction.EAST);
                        default  -> Parsed.unknown();
                    };
                }
                Direction direction = Direction.fromInput(head);
                return (direction != null) ? Parsed.move(direction) : Parsed.unknown();
            }
        }
    }

    private static Integer parsePositiveInt(String text) {
        try {
            int value = Integer.parseInt(text);
            return (value > 0) ? value : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
