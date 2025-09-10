package map;


public class Dungeon {

    private final int width, height;
    private final Tile[][] map;
    private final int start = 0;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        map = new Tile[height][width];


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y][x] = new Tile(TileType.FLOOR);
            }
        }

        // Walls around the map
        for (int x = 0; x < width; x++) {
            map[0][x] = new Tile(TileType.WALL);
            map[height - 1][x] = new Tile(TileType.WALL);
        }
        for (int y = 0; y < height; y++) {
            map[y][0] = new Tile(TileType.WALL);
            map[y][width - 1] = new Tile(TileType.WALL);
        }

        //Random placed walls
        for(int x = 2; x < 7; x++){
            map[3][x] = new Tile(TileType.WALL);
        }
        for(int x = 5; x < 9; x++){
            map[6][x] = new Tile(TileType.WALL);
        }
        map[7][3] = new Tile(TileType.WALL);
        map[7][2] = new Tile(TileType.WALL);
        map[5][2] = new Tile(TileType.WALL);
        map[5][1] = new Tile(TileType.WALL);
        map[1][4] = new Tile(TileType.WALL);
        map[4][7] = new Tile(TileType.ROOM);
        map[7][4] = new Tile(TileType.ROOM);
        map[2][1] = new Tile(TileType.ROOM);

        printDungeon();
    }

    //If you want to print the map
    private void printDungeon() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                System.out.print(map[y][x] + "  ");
            System.out.println();
        }
    }

    public boolean isWalkable(int x, int y) {
        return map[y][x].getType() == TileType.FLOOR || map[y][x].getType() == TileType.ROOM;
    }

}


