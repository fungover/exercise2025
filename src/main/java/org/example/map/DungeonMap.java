package org.example.map;

import org.example.entities.Tile;
import org.example.entities.Player;
import org.example.service.ItemService;

public class DungeonMap {    ;
    private Tile[][] dungeon;
    private String heart = "\u2764";

    public DungeonMap(Tile[][] dungeon) {
        this.dungeon = dungeon;
    }

    public Tile[][] getDungeon() {
        return dungeon;
    }

    //method to provide correct symbol for the tile
    private String getSymbolForTile(Tile tile, int row, int col, Player player) {
        if (row == player.getX() && col == player.getY()) return "X";
        if (!tile.isVisited()) return ".";
        if (!tile.isWalkable()) return "#";
        if (tile.getEnemy() != null) return "E";
        if (tile.getItem() != null) {
            return "map".equals(tile.getItem().getType()) ? "M" : "P"; // map or potion
        }
        return " ";
    }

    //Print the map
    public void print(Player player) {
        int rows = dungeon.length;
        int cols = dungeon[0].length;

        System.out.println("\n\nDungeon map:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = dungeon[i][j];
                System.out.print("[" + getSymbolForTile(tile, i, j, player) + "]");
            }
            System.out.println();
        }

        System.out.println("\n [?] = Unexplored tile, [X] = Your location, [E] = Enemy, [#] = Wall, [P] = Potion, [M] = Map \n");
        System.out.println("Hero name: " + player.getName() + " " + heart + "HP: " + player.getHealth() + "/100");
        ItemService.printInventory(player);
        System.out.println("Commands: [go north] [go south] [go west] [go east] [look] [attack] [quit]");
        Tile currentTile = dungeon[player.getX()][player.getY()];
        String onCurrentTile = "\nOn current tile: ";

        if (currentTile.getEnemy() != null) {
            System.out.println(onCurrentTile + currentTile.getEnemy().getName() + " HP: " + currentTile.getEnemy().getHealth() + " Attack: " + currentTile.getEnemy().getDamage());
        } else if (currentTile.getItem() != null) {
            System.out.println(onCurrentTile + currentTile.getItem().getName());
        }  else {
            System.out.println(onCurrentTile + "Nothing");
        }
    }
}