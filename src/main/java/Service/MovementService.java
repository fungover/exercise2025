package Service;

import Entities.*;
import Map.*;

public record MovementService(DungeonMap map) {

    public void move(Player p, int dx, int dy) {
        int nx = p.getX() + dx;
        int ny = p.getY() + dy;
        if (!map.inBounds(nx, ny)) {
            System.out.println("You bump into the edge of the world.");
            return;
        }
        Tile dest = map.tile(nx, ny);
        if (!dest.getType().isWalkable()) {
            System.out.println("You can't move there.");
            return;
        }
        p.setPos(nx, ny);
        System.out.println("You moved to (" + nx + "," + ny + ").");
        if (dest.getItem() != null) {
            p.addItem(dest.getItem());
            dest.setItem(null);
        }
        map.describeCurrentTile(p);
    }
}
