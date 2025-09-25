package utils;

import entities.Player;
import map.Dungeon;
import map.Tile;
import map.TileType;

import java.util.List;

public class Printer {

    public static void printWelcome() {
        System.out.println("=== Dungeon Crawler ===");
        System.out.println("Type 'help' to see available commands.");
    }

    public static void printHelp() {
        System.out.println("=== Commands ===");
        System.out.println("Movement: n/s/e/w or move north|south|east|west");
        System.out.println("Combat:   attack");
        System.out.println("Items:    inventory, use <item>");
        System.out.println("Explore:  look");
        System.out.println("Other:    help, quit");
    }

    /** Short status each turn */
    public static void printStatus(Player p) {
        System.out.println("---------------------------------");
        System.out.println(p.getName() +
                "  HP: " + p.getHealth() + "/" + p.getMaxHealth() +
                "  Pos: (" + p.getX() + "," + p.getY() + ")");
    }

    /** Info about the current tile */
    public static void printTileInfo(Dungeon d, int x, int y) {
        Tile t = d.getTile(x, y);
        if (t == null) {
            System.out.println("Here: (outside the dungeon)");
            return;
        }
        System.out.print("Here: " + t.getType());
        if (TileType.ENEMY.equals(t.getType()) && t.getEnemy() != null) {
            System.out.print(" → Enemy: " + t.getEnemy().getType() +
                    " (HP " + t.getEnemy().getHealth() + ")");
        }
        if (TileType.ITEM.equals(t.getType()) && t.getItem() != null) {
            System.out.print(" → Item: " + t.getItem().getName());
        }
        if (TileType.EXIT.equals(t.getType())) {
            System.out.print(" → You see an exit here!");
        }
        System.out.println();
    }

    public static void printInventory(Player p) {
        List<entities.Item> inv = p.getInventory();
        if (inv.isEmpty()) {
            System.out.println("Inventory: (empty)");
            return;
        }
        System.out.println("Inventory:");
        for (int i = 0; i < inv.size(); i++) {
            System.out.println("  " + i + ": " + inv.get(i).getName());
        }
    }

    /** Simple mini map */
    public static void printMiniMap(Dungeon d, Player p, int radius) {
        int px = p.getX();
        int py = p.getY();
        for (int y = py - radius; y <= py + radius; y++) {
            StringBuilder row = new StringBuilder();
            for (int x = px - radius; x <= px + radius; x++) {
                if (!d.isWithinBounds(x, y)) { row.append('#'); continue; }
                if (x == px && y == py) { row.append('P'); continue; }
                Tile t = d.getTile(x, y);
                String type = t.getType();
                char c =
                        TileType.WALL.equals(type)  ? '#'
                                : TileType.ENEMY.equals(type) ? 'E'
                                : TileType.ITEM.equals(type)  ? 'I'
                                : TileType.START.equals(type) ? 'S'
                                : TileType.EXIT.equals(type)  ? 'X'
                                : '.';
                row.append(c);
            }
            System.out.println(row);
        }
    }

    public static void info(String msg)  { System.out.println(msg); }
    public static void error(String msg) { System.out.println("Error: " + msg); }
}
