package utils;

import java.util.Locale;

/**
 * Mycket enkel kommandoparsning för CLI.
 * Returnerar ett Command-objekt med action + arg.
 *
 * Stödda kommandon (exempel):
 * - "move north" / "move n" / "n"
 * - "move south" / "s" / "move s"
 * - "attack"
 * - "use potion" / "use 1"
 * - "inventory"
 * - "look"
 * - "help"
 * - "quit"
 */
public class InputParser {

    /** Resultatet av parsning: action + ev. argument. */
    public static class Command {
        private final String action; // t.ex. "move", "attack", "use", "inventory"
        private final String arg;    // t.ex. "north", "potion", "1"

        public Command(String action, String arg) {
            this.action = action;
            this.arg = arg;
        }
        public String action() { return action; }
        public String arg()    { return arg; }
    }

    public Command parse(String line) {
        if (line == null) return new Command("unknown", "");
        String s = line.trim().toLowerCase(Locale.ROOT);
        if (s.isEmpty()) return new Command("empty", "");

        // Snabba alias för rörelse
        if (s.equals("n")) return new Command("move", "north");
        if (s.equals("s")) return new Command("move", "south");
        if (s.equals("e")) return new Command("move", "east");
        if (s.equals("w")) return new Command("move", "west");

        // Dela upp i ord
        String[] parts = s.split("\\s+");
        String first = parts[0];

        // move <dir>
        if (first.equals("move")) {
            String dir = parts.length > 1 ? parts[1] : "";
            dir = normalizeDirection(dir);
            return new Command("move", dir);
        }

        // attack
        if (first.equals("attack")) {
            return new Command("attack", "");
        }

        // use <arg>
        if (first.equals("use")) {
            String arg = parts.length > 1 ? s.substring(s.indexOf(' ') + 1).trim() : "";
            return new Command("use", arg);
        }

        // inventory / inv
        if (first.equals("inventory") || first.equals("inv")) {
            return new Command("inventory", "");
        }

        // look / l
        if (first.equals("look") || first.equals("l")) {
            return new Command("look", "");
        }

        // help / h / ?
        if (first.equals("help") || first.equals("h") || first.equals("?")) {
            return new Command("help", "");
        }

        // quit / exit
        if (first.equals("quit") || first.equals("exit")) {
            return new Command("quit", "");
        }

        return new Command("unknown", s);
    }

    private String normalizeDirection(String dir) {
        if (dir == null) return "";
        switch (dir) {
            case "n": case "north": return "north";
            case "s": case "south": return "south";
            case "e": case "east":  return "east";
            case "w": case "west":  return "west";
            default: return "";
        }
    }
}