package utils;

public class InputParser {

    public record Command(String action, String arg) {}

    public Command parse(String raw) {
        if (raw == null) return new Command("empty", null);

        // normalize: lowercase, remove commas/angle-brackets, collapse spaces
        String s = raw.toLowerCase()
                .replaceAll("[,<>]", " ")
                .replaceAll("\\s+", " ")
                .trim();

        if (s.isEmpty()) return new Command("empty", null);

        // single-letter movement aliases
        if (s.equals("n")) return new Command("move", "north");
        if (s.equals("s")) return new Command("move", "south");
        if (s.equals("e")) return new Command("move", "east");
        if (s.equals("w")) return new Command("move", "west");

        // explicit move
        if (s.startsWith("move ")) {
            String dir = s.substring(5).trim();
            return new Command("move", dir);
        }
        if (s.equals("move")) return new Command("move", null);

        // use item (by name or index)
        if (s.startsWith("use ")) {
            return new Command("use", s.substring(4).trim());
        }
        if (s.equals("use")) return new Command("use", null);

        // simple commands
        switch (s) {
            case "attack":     return new Command("attack", null);
            case "inventory":
            case "inv":        return new Command("inventory", null);
            case "look":
            case "l":          return new Command("look", null);
            case "help":
            case "h":
            case "?":          return new Command("help", null);
            case "quit":
            case "exit":       return new Command("quit", null);
        }

        // If the user typed multiple tokens like "inventory use item",
        // still honor the first word.
        String first = s.split(" ", 2)[0];
        switch (first) {
            case "inventory": return new Command("inventory", null);
            case "attack":    return new Command("attack", null);
            case "look":      return new Command("look", null);
            case "help":      return new Command("help", null);
            case "quit":      return new Command("quit", null);
            case "use":       return new Command("use", null);
            case "move":      return new Command("move", null);
        }

        return new Command("unknown", s);
    }
}
