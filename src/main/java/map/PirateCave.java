package map;

import entities.Player;
import entities.Item;
import entities.Enemy;
import enemies.EnemyFactory;

/**
 * PirateCave representerar hela vår karta med fiender och föremål
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
        return (row % 2 == 0) && (col % 2 == 1);
    }

    private boolean isCrossing(int row, int col) {
        return (row % 2 == 0) && (col % 2 == 0) &&
                !isCornerPosition(row, col);
    }

    public void displayMap(Player player) {
        System.out.println("\n⚔️ === PIRATGROTTAN === ⚔️");

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
                    // Show the player (the player has the highest priority)
                    System.out.print(player.getSymbol());
                } else {
                    // Show tile (which can be enemy, item or normal symbol)
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

    // === OBJECT HANDLING ===
    public boolean placeItem(Item item, int gameX, int gameY) {
        if (!isValidGamePosition(gameX, gameY)) {
            return false;
        }

        int displayX = gameX * 2 + 1;
        int displayY = gameY * 2 + 1;

        Tile tile = getTile(displayX, displayY);
        if (tile != null && tile.canPlaceThings() && !tile.hasItem()) {
            tile.placeItem(item);
            return true;
        }
        return false;
    }

    public Item getItemAt(int gameX, int gameY) {
        if (!isValidGamePosition(gameX, gameY)) {
            return null;
        }

        int displayX = gameX * 2 + 1;
        int displayY = gameY * 2 + 1;

        Tile tile = getTile(displayX, displayY);
        return tile != null ? tile.getItem() : null;
    }

    public Item removeItemAt(int gameX, int gameY) {
        if (!isValidGamePosition(gameX, gameY)) {
            return null;
        }

        int displayX = gameX * 2 + 1;
        int displayY = gameY * 2 + 1;

        Tile tile = getTile(displayX, displayY);
        return tile != null ? tile.removeItem() : null;
    }

    public boolean hasItemAt(int gameX, int gameY) {
        Item item = getItemAt(gameX, gameY);
        return item != null;
    }

    // === ENEMY MANAGEMENT ===
    public boolean placeEnemy(Enemy enemy, int gameX, int gameY) {
        if (!isValidGamePosition(gameX, gameY)) {
            return false;
        }

        int displayX = gameX * 2 + 1;
        int displayY = gameY * 2 + 1;

        Tile tile = getTile(displayX, displayY);
        if (tile != null && tile.canPlaceThings() && !tile.hasEnemy()) {
            tile.placeEnemy(enemy);
            enemy.setPosition(gameX, gameY); // Sätt fiendens position
            return true;
        }
        return false;
    }

    public Enemy getEnemyAt(int gameX, int gameY) {
        if (!isValidGamePosition(gameX, gameY)) {
            return null;
        }

        int displayX = gameX * 2 + 1;
        int displayY = gameY * 2 + 1;

        Tile tile = getTile(displayX, displayY);
        return tile != null ? tile.getEnemy() : null;
    }

    public Enemy removeEnemyAt(int gameX, int gameY) {
        if (!isValidGamePosition(gameX, gameY)) {
            return null;
        }

        int displayX = gameX * 2 + 1;
        int displayY = gameY * 2 + 1;

        Tile tile = getTile(displayX, displayY);
        return tile != null ? tile.removeEnemy() : null;
    }

    public boolean hasEnemyAt(int gameX, int gameY) {
        Enemy enemy = getEnemyAt(gameX, gameY);
        return enemy != null && enemy.isAlive();
    }

    // === COLLISION DETECTION ===
    public boolean canPlayerMoveTo(int gameX, int gameY) {
        if (!isValidGamePosition(gameX, gameY)) {
            return false;
        }

        int displayX = gameX * 2 + 1;
        int displayY = gameY * 2 + 1;

        Tile tile = getTile(displayX, displayY);
        return tile != null && tile.canWalkOn();
    }

    // === RANDOM GENERATION ===
    public void populateWithEnemies(int enemyCount) {
        System.out.println("🏴‍☠️ Placerar " + enemyCount + " fiender i grottan...");

        int placed = 0;
        int attempts = 0;
        int maxAttempts = enemyCount * 10; // Undvik oändlig loop

        while (placed < enemyCount && attempts < maxAttempts) {
            int randomX = (int)(Math.random() * gridWidth);
            int randomY = (int)(Math.random() * gridHeight);

            // Avoid placing enemies near the starting position (0,0)
            if (randomX <= 1 && randomY <= 1) {
                attempts++;
                continue;
            }

            // Check if the position is available
            if (canPlaceEnemyAt(randomX, randomY)) {
                Enemy enemy = EnemyFactory.createRandomEnemy();
                if (placeEnemy(enemy, randomX, randomY)) {
                    placed++;
                    System.out.println("   Placerade " + enemy.getName() +
                            " på position (" + randomX + ", " + randomY + ")");
                }
            }
            attempts++;
        }

        System.out.println("✅ Placerade " + placed + " fiender totalt.");
    }

    private boolean canPlaceEnemyAt(int gameX, int gameY) {
        if (!isValidGamePosition(gameX, gameY)) {
            return false;
        }

        int displayX = gameX * 2 + 1;
        int displayY = gameY * 2 + 1;

        Tile tile = getTile(displayX, displayY);
        return tile != null && tile.canPlaceThings() &&
                !tile.hasEnemy() && !tile.hasItem();
    }

    // === HELP METHODS ===
    public void displayMap() {
        displayMap(null);
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

    // Getters
    public int getGridWidth() { return gridWidth; }
    public int getGridHeight() { return gridHeight; }
    public int getDisplayWidth() { return displayWidth; }
    public int getDisplayHeight() { return displayHeight; }
}