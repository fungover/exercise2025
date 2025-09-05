package service;

import entities.Player;
import map.Dungeon;
import map.Tile;
import map.TileType;
import utils.Printer;

/**
 * MovementService ansvarar för att flytta spelaren
 * och trigga events på nya rutan.
 */
public class MovementService {

    /**
     * Flytta spelaren i angiven riktning.
     *
     * @param player  spelaren
     * @param dungeon kartan
     * @param dir     riktning ("north","south","east","west")
     */
    public static void move(Player player, Dungeon dungeon, String dir) {
        int dx = 0, dy = 0;
        switch (dir) {
            case "north": dy = -1; break;
            case "south": dy =  1; break;
            case "east":  dx =  1; break;
            case "west":  dx = -1; break;
            default:
                Printer.error("Ogiltig riktning.");
                return;
        }

        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        // Validera bounds + vägg
        if (!dungeon.isWithinBounds(newX, newY)) {
            Printer.error("Du kan inte gå utanför kartan.");
            return;
        }
        if (!dungeon.isWalkable(newX, newY)) {
            Printer.error("En vägg blockerar vägen.");
            return;
        }

        // Flytta spelaren
        player.move(dx, dy);
        Tile tile = dungeon.getTile(newX, newY);

        // Trigga händelser
        handleTileEvents(player, tile);
    }

    private static void handleTileEvents(Player player, Tile tile) {
        if (tile == null) return;

        // Item
        if (TileType.ITEM.equals(tile.getType()) && tile.getItem() != null) {
            service.InventoryService.addItem(player, tile.pickUpItem(), service.InventoryService.DEFAULT_MAX_SLOTS);
        }


        // Enemy
        if (TileType.ENEMY.equals(tile.getType()) && tile.getEnemy() != null) {
            Printer.info("En " + tile.getEnemy().getType() + " står i vägen!");
        }

        // Exit
        if (TileType.EXIT.equals(tile.getType())) {
            Printer.info("Du har hittat utgången!");
        }
    }
}
