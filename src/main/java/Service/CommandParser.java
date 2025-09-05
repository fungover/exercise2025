package Service;

public class CommandParser {
    public enum Cmd {
        MOVE_N, MOVE_S, MOVE_W, MOVE_E, LOOK, ATTACK, INVENTORY, USE_POTION, HELP, QUIT, INVALID
    }

    public Cmd parse(String input) {
        String s = input.toLowerCase().trim();
        return switch (s) {
            case "move north", "n", "north" -> Cmd.MOVE_N;
            case "move south", "s", "south" -> Cmd.MOVE_S;
            case "move west",  "w", "west"  -> Cmd.MOVE_W;
            case "move east",  "e", "east"  -> Cmd.MOVE_E;
            case "look", "l"                -> Cmd.LOOK;
            case "attack", "a"              -> Cmd.ATTACK;
            case "inventory", "i"           -> Cmd.INVENTORY;
            case "use potion", "use", "u"   -> Cmd.USE_POTION;
            case "help", "h", "?"           -> Cmd.HELP;
            case "quit", "q", "exit"        -> Cmd.QUIT;
            default                         -> Cmd.INVALID;
        };
    }

    public void printHelp() {
        System.out.println("""
        Available commands:

        Movement:
          north / n     - Move north
          south / s     - Move south
          east  / e     - Move east
          west  / w     - Move west

        Actions:
          look / l      - Look around your current tile
          attack / a    - Attack an enemy on your tile
          use potion    - Drink a potion from inventory
          inventory / i - Show your inventory

        Other:
          help / h      - Show this help menu again
          quit / q      - Exit the game
        """);
    }
}
