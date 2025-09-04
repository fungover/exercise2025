package org.example.map;

//used for mock testing
public class mockDungeonGenerator extends DungeonGenerator {
    private boolean[][] map;

    // true = valid position; false = wall

    public mockDungeonGenerator(boolean[][] map) {
        super(map[0].length, map.length); // width and height

        this.map = map;
    }

    @Override protected void generateDungeon() {
        // No-op for test
    }


    public boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && y < map.length && x < map[0].length && map[y][x];
    }
}
