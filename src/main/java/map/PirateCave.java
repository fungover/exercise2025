package map;

import entities.Player;

/**
 * PirateCave representerar hela vår karta
 */
public class PirateCave {
    private Tile[][] map;
    private int gridWidth;
    private int gridHeight;
    private int displayWidth;
    private int displayHeight;

    public PirateCave(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.displayWidth = gridWidth * 2 + 1;
        this.displayHeight = gridHeight * 2 + 1;
        this.map = new Tile[displayHeight][displayWidth];
        createGridMap();
    }

    private void createGridMap() {
        for (int row = 0; row < displayHeight; row++) {
            for (int col = 0; col < displayWidth; col++) {
                if (isCornerPosition(row, col)) {
                    map[row][col] = createCornerTile(row, col);
                }
                else if (isHorizontalLine(row, col)) {
                    map[row][col] = new Tile('─', "Horisontell linje");
                }
                else if (isVerticalLine(row, col)) {
                    map[row][col] = new Tile('│', "Vertikal linje");
                }
                else if (isCrossing(row, col)) {
                    map[row][col] = new Tile('┼', "Korsning av linjer");
                }
                else {
                    map[row][col] = new Tile(' ', "Tom spelruta");
                }
            }
        }
    }

    private boolean isCornerPosition(int row, int col) {
        return (row == 0 || row == displayHeight-1) &&
                (col == 0 || col == displayWidth-1);
    }

    private Tile createCornerTile(int row, int col) {
        if (row == 0 && col == 0) {
            return new Tile('┌', "Övre vänstra hörnet");
        } else if (row == 0 && col == displayWidth-1) {
            return new Tile('┐', "Övre högra hörnet");
        } else if (row == displayHeight-1 && col == 0) {
            return new Tile('└', "Nedre vänstra hörnet");
        } else {
            return new Tile('┘', "Nedre högra hörnet");
        }
    }

    private boolean isHorizontalLine(int row, int col) {
        return (row % 2 == 0) && (col % 2 == 1);
    }

    private boolean isVerticalLine(int row, int col) {
        return (row % 2 == 1) && (col % 2 == 0);
    }

    private boolean isCrossing(int row, int col) {
        return (row % 2 == 0) && (col % 2 == 0) &&
                !isCornerPosition(row, col);
    }

    public void displayMap(Player player) {
        System.out.println("\n🏴‍☠️ === PIRATGROTTAN === 🏴‍☠️");

        int playerDisplayX = -1;
        int playerDisplayY = -1;
        if (player != null) {
            int[] playerPos = player.getDisplayPosition();
            playerDisplayX = playerPos[0];
            playerDisplayY = playerPos[1];
        }

        for (int row = 0; row < displayHeight; row++) {
            for (int col = 0; col < displayWidth; col++) {
                if (player != null && col == playerDisplayX && row == playerDisplayY) {
                    // Visa spelaren (spelaren har högst prioritet)
                    System.out.print(player.getSymbol());
                } else {
                    // Visa tile (som kan vara föremål eller normal symbol)
                    System.out.print(map[row][col].getDisplaySymbol());
                }
            }
            System.out.println();
        }

        if (player != null) {
            System.out.println("👤 " + player.getName() + " @ spelposition (" +
                    player.getX() + "," + player.getY() + ")");
        }
        System.out.println("🗺️  Rutnät: " + gridWidth + "x" + gridHeight + " spelrutor");
    }

    /**
     * Placerar ett föremål på kartan vid spelkoordinater
     * @param item Föremålet att placera
     * @param gameX X-koordinat (spelkoordinater)
     * @param gameY Y-koordinat (spelkoordinater)
     * @return true om föremålet placerades, false om positionen är ogiltig
     */
    public boolean placeItem(entities.Item item, int gameX, int gameY) {
        if (!isValidGamePosition(gameX, gameY)) {
            return false;
        }

        // Konvertera till display-koordinater
        int displayX = gameX * 2 + 1;
        int displayY = gameY * 2 + 1;

        Tile tile = getTile(displayX, displayY);
        if (tile != null && tile.canWalkOn()) {
            tile.placeItem(item);
            return true;
        }

        return false;
    }

    /**
     * Hämtar föremål från en spelposition
     * @param gameX X-koordinat (spelkoordinater)
     * @param gameY Y-koordinat (spelkoordinater)
     * @return Föremålet eller null om inget finns
     */
    public entities.Item getItemAt(int gameX, int gameY) {
        if (!isValidGamePosition(gameX, gameY)) {
            return null;
        }

        int displayX = gameX * 2 + 1;
        int displayY = gameY * 2 + 1;

        Tile tile = getTile(displayX, displayY);
        return tile != null ? tile.getItem() : null;
    }

    /**
     * Tar bort föremål från en spelposition
     * @param gameX X-koordinat (spelkoordinater)
     * @param gameY Y-koordinat (spelkoordinater)
     * @return Föremålet som togs bort, eller null
     */
    public entities.Item removeItemAt(int gameX, int gameY) {
        if (!isValidGamePosition(gameX, gameY)) {
            return null;
        }

        int displayX = gameX * 2 + 1;
        int displayY = gameY * 2 + 1;

        Tile tile = getTile(displayX, displayY);
        return tile != null ? tile.removeItem() : null;
    }

    /**
     * Kontrollerar om det finns ett föremål på en spelposition
     */
    public boolean hasItemAt(int gameX, int gameY) {
        entities.Item item = getItemAt(gameX, gameY);
        return item != null;
    }

    public void displayMap() {
        displayMap(null);
    }

    public boolean canPlayerMoveTo(int gameX, int gameY) {
        if (!isValidGamePosition(gameX, gameY)) {
            return false;
        }

        int displayX = gameX * 2 + 1;
        int displayY = gameY * 2 + 1;

        Tile tile = getTile(displayX, displayY);
        return tile != null && tile.canWalkOn();
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= displayWidth || y < 0 || y >= displayHeight) {
            return null;
        }
        return map[y][x];
    }

    public boolean isValidGamePosition(int gameX, int gameY) {
        return gameX >= 0 && gameX < gridWidth && gameY >= 0 && gameY < gridHeight;
    }

    public int getGridWidth() { return gridWidth; }
    public int getGridHeight() { return gridHeight; }
    public int getDisplayWidth() { return displayWidth; }
    public int getDisplayHeight() { return displayHeight; }
}