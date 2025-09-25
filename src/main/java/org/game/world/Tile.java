package org.game.world;

import org.game.utils.Colors;

public class Tile {
    private boolean walkable;
    private String symbol;
    private Room room;

    public Tile(boolean walkable, String symbol) {
        this.walkable = walkable;
        this.symbol = symbol;
        this.room = null;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public String getSymbol() {
        if (walkable) {
            if (room != null) {
                switch (room.getType()) {
                    case "enemy": return Colors.redColor + "[E]" + Colors.resetColor;
                    case "treasure": return Colors.yellowColor + "[T]" + Colors.resetColor;
                    case "boss": return Colors.magentaColor + "[B]" + Colors.resetColor;
                    default: return Colors.greenColor + symbol + Colors.resetColor;
                }
            }
            return Colors.greenColor + symbol + Colors.resetColor;
        } else {
            return Colors.whiteBackground + symbol + Colors.resetColor;
        }
    }

    public Room getRoom() {return room;}
    public void setRoom(Room room) {this.room = room;}


}
