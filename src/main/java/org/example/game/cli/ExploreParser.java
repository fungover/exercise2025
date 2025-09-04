package org.example.game.cli;

import org.example.utils.Direction;

public final class ExploreParser {

    public enum ExploreCommandType { MOVE, INVENTORY, LOOT, TAKE, USE, HELP, MAP, QUIT, UNKNOWN }

    public record ExploreCommand(ExploreCommandType type, Direction direction, Integer oneBasedIndex, boolean takeAllFlag) {

        public static ExploreCommand move(Direction d) {
            return new ExploreCommand(ExploreCommandType.MOVE, d, null, false);
        }

        public static ExploreCommand inventory() {
            return new ExploreCommand(ExploreCommandType.INVENTORY, null, null, false);
        }

        public static ExploreCommand loot() {
            return new ExploreCommand(ExploreCommandType.LOOT, null, null, false);
        }

        public static ExploreCommand takeIndex(int idx) {
            return new ExploreCommand(ExploreCommandType.TAKE, null, idx, false);
        }

        public static ExploreCommand takeAll() {
            return new ExploreCommand(ExploreCommandType.TAKE, null, null, true);
        }

        public static ExploreCommand useIndex(int idx) {
            return new ExploreCommand(ExploreCommandType.USE, null, idx, false);
        }

        public static ExploreCommand help() {
            return new ExploreCommand(ExploreCommandType.HELP, null, null, false);
        }

        public static ExploreCommand map() {
            return new ExploreCommand(ExploreCommandType.MAP, null, null, false);
        }

        public static ExploreCommand quit() {
            return new ExploreCommand(ExploreCommandType.QUIT, null, null, false);
        }

        public static ExploreCommand unknown() {
            return new ExploreCommand(ExploreCommandType.UNKNOWN, null, null, false);
        }
        }

    private ExploreParser() {}

    public static ExploreCommand parse(String line) {
        if (line == null || line.isBlank()) return ExploreCommand.unknown();

        String inputText = line.trim().toLowerCase();
        String[] parts = inputText.split("\\s+");
        String head = parts[0];

        switch (head) {
            case "q", "quit" -> { return ExploreCommand.quit(); }
            case "h", "help" -> { return ExploreCommand.help(); }
            case "m", "map"  -> { return ExploreCommand.map(); }
            case "i", "inventory" -> { return ExploreCommand.inventory(); }
            case "l", "loot" -> { return ExploreCommand.loot(); }

            case "take" -> {
                if (parts.length < 2) return ExploreCommand.unknown();
                String arg = parts[1];
                if ("all".equals(arg)) return ExploreCommand.takeAll();
                Integer idx = parsePositiveInt(arg);
                return (idx != null) ? ExploreCommand.takeIndex(idx) : ExploreCommand.unknown();
            }

            case "use" -> {
                if (parts.length < 2) return ExploreCommand.unknown();
                Integer idx = parsePositiveInt(parts[1]);
                return (idx != null) ? ExploreCommand.useIndex(idx) : ExploreCommand.unknown();
            }

            // Movement: only WASD or full words.
            default -> {
                if (head.length() == 1) {
                    return switch (head) {
                        case "w" -> ExploreCommand.move(Direction.NORTH);
                        case "a" -> ExploreCommand.move(Direction.WEST);
                        case "s" -> ExploreCommand.move(Direction.SOUTH);
                        case "d" -> ExploreCommand.move(Direction.EAST);
                        default  -> ExploreCommand.unknown();
                    };
                }
                Direction direction = Direction.fromInput(head);
                return (direction != null) ? ExploreCommand.move(direction) : ExploreCommand.unknown();
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
