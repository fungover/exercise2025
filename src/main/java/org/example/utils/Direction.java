package org.example.utils;

public enum Direction {
    NORTH(0, -1, "north"),
    SOUTH(0,  1, "south"),
    WEST(-1,  0, "west"),
    EAST(1,   0, "east");

    public final int dx;
    public final int dy;
    private final String[] aliases;

    Direction(int dx, int dy, String... aliases) {
        this.dx = dx;
        this.dy = dy;
        this.aliases = aliases;
    }

    /** Map only WASD or full words (north/south/west/east) to a Direction. */
    public static Direction fromInput(String token) {
        if (token == null) return null;
        String t = token.trim().toLowerCase();

        // WASD
        switch (t) {
            case "w": return NORTH;
            case "a": return WEST;
            case "s": return SOUTH;
            case "d": return EAST;
        }

        for (Direction d : values()) {
            for (String a : d.aliases) {
                if (a.equals(t)) return d;
            }
        }
        return null;
    }
}
