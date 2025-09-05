package utils;

import entities.Player;
import map.Dungeon;
import map.Tile;
import map.TileType;

import java.util.List;

public class Printer {

    public static void printWelcome() {
        System.out.println("=== Dungeon Crawler ===");
        System.out.println("Skriv 'help' för kommandon.");
    }

    public static void printHelp() {
        System.out.println("=== Kommandon ===");
        System.out.println("Rörelse:");
        System.out.println("  n/s/e/w eller move north|south|east|west");
        System.out.println();
        System.out.println("Strid:");
        System.out.println("  attack   (attackerar fiende på samma ruta)");
        System.out.println();
        System.out.println("Föremål:");
        System.out.println("  inventory   (lista dina items)");
        System.out.println("  use <item>  (ange namn eller index, ex. 'use potion' eller 'use 0')");
        System.out.println();
        System.out.println("Utforskning:");
        System.out.println("  look   (visa karta runt dig)");
        System.out.println();
        System.out.println("Övrigt:");
        System.out.println("  help   (visa denna lista)");
        System.out.println("  quit   (avsluta spelet)");
    }


    public static void printStatus(Player p) {
        System.out.println("Spelare: " + p.getName() +
                "  HP: " + p.getHealth() + "/" + p.getMaxHealth() +
                "  Pos: (" + p.getX() + "," + p.getY() + ")");
    }

    public static void printInventory(Player p) {
        List<entities.Item> inv = p.getInventory();
        if (inv.isEmpty()) {
            System.out.println("Inventarie: (tomt)");
            return;
        }
        System.out.println("Inventarie:");
        for (int i = 0; i < inv.size(); i++) {
            System.out.println("  " + i + ": " + inv.get(i).getName());
        }
    }

    /** Skriv enkel info om aktuell ruta. */
    public static void printTileInfo(Dungeon d, int x, int y) {
        Tile t = d.getTile(x, y);
        if (t == null) {
            System.out.println("Utanför kartan.");
            return;
        }
        System.out.print("Rutan är: " + t.getType());
        if (TileType.ENEMY.equals(t.getType()) && t.getEnemy() != null) {
            System.out.print(" (Fiende HP: " + t.getEnemy().getHealth() + ")");
        }
        if (TileType.ITEM.equals(t.getType()) && t.getItem() != null) {
            System.out.print(" (Item: " + t.getItem().getName() + ")");
        }
        System.out.println();
    }

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
    public static void error(String msg) { System.out.println("Fel: " + msg); }
}
