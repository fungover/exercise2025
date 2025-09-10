package dev.ronja.dungeon.map;

import java.util.Random;

/**
 * A Grid in the CLI, now with Tiles
 **/

public class Dungeon {
    private final int w, h;
    private final Tile[][] grid;
    private final Random r = new Random();

    public Dungeon(int w, int h) {
        this.w = w;
        this.h = h;
        this.grid = new Tile[h][w];
        gen();
    }

    /**
     * Outer walls and floors
     */
    private void gen() {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                boolean border = x == 0 || y == 0 || x == w - 1 || y == h - 1;
                grid[y][x] = new Tile(border ? TileType.WALL : TileType.FLOOR);
            }
        }

        int walls = (w * h) / 8;
        for (int i = 0; i < (walls); i++) {
            int x = r.nextInt(w - 2) + 1;
            int y = r.nextInt(h - 2) + 1;
            grid[y][x].setType(TileType.WALL);
        }
    }

    public boolean inBounds(Position p) {
        return p.x() >= 0 && p.x() < w && p.y() >= 0 && p.y() < h;
    }

    public boolean isWalkable(Position p) {
        return inBounds(p) && grid[p.y()][p.x()].isWalkable();
    }
    /** Get tile object **/
    public Tile tileAt(Position p) { return grid[p.y()][p.x()];}

    /** To get rid of wall bug I need at least one open neighbor tile **/
    public boolean hasOpenNeighbor(Position p) {
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : dirs) {
            int nx = p.x() + d[0], ny = p.y() + d[1];
            if (nx <= 0 || ny <= 0 || nx >= w-1 || ny >= h-1) continue;
            if (grid[ny][nx].getType() == TileType.FLOOR) return true;
        }
        return false;
    }

    public void carveExitAround(Position p) {
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int i = 0; i < dirs.length; i++) {
            int j = r.nextInt(dirs.length);
            int[] tmp = dirs[i]; dirs[i] = dirs[j]; dirs[j] = tmp;
        }

        for (int [] d : dirs) {
            int nx = p.x() + d[0], ny = p.y() + d[1];
            if (nx <= 0 || ny <= 0 || nx >= w-1 || ny >= h-1) continue;
            if (grid[ny][nx].getType() == TileType.WALL) {
                grid[ny][nx].setType(TileType.FLOOR);
                return;
            }

        }
    }

    /**
     * Return a FLOOR tile with fallback
     **/
    public Position anyOpenFloor() {
        for (int tries = 0; tries < 200; tries++) {
            int x = r.nextInt(w - 2) + 1;
            int y = r.nextInt(h - 2) + 1;
            if (grid[y][x].getType() == TileType.FLOOR) {
                Position p = new Position(x, y);
                if (!hasOpenNeighbor(p)) carveExitAround(p);
                return p;
            }
        }

        Position p = anyFloor();
        if (!hasOpenNeighbor(p)) carveExitAround(p);
        return p;
    }
    public Position anyFloor() {
        for (int tries = 0; tries < 200; tries++) {
            int x = r.nextInt(w -2) + 1;
            int y = r.nextInt(h -2) + 1;
            if (grid[y][x].getType() == TileType.FLOOR) return new Position(x, y);
        }
        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {
                if (grid[y][x].getType() == TileType.FLOOR) return new Position(x, y);
            }
        }
        return new Position(1, 1);
    }

    public String render(Position player, Position enemy) {
        var sb = new StringBuilder();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                var p = new Position(x, y);
                if (p.equals(player)) sb.append('P');
                else if (p.equals(enemy)) sb.append('E');
                else sb.append(grid[y][x].glyph());
            }
            sb.append('\n');
        }
        return sb.toString();

    }
}
