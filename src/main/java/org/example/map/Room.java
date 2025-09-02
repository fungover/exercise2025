package org.example.map;

public class Room {
    private final Dungeon dungeon;
    private final String name;

    public Room(String name, int rows, int columns) {
        this.name = name;
        this.dungeon = new Dungeon(rows, columns);
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public String getName() {
        return name;
    }
}
