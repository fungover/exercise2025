package map;

import entities.Item;

public class Tile {
    private final TileType tileType;
    private Item item;

    public Tile(TileType type) {
        this.tileType = type;
    }

    public TileType getType() {
        return this.tileType;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean hasItem() {
        return item != null;
    }

    @Override
    public String toString(){
        if(this.tileType == TileType.WALL){
            return "#";
        }
        else if(this.tileType == TileType.FLOOR){
            return ".";
        }
        else if(this.tileType == TileType.ROOM) {
            return "R";
        }
        else{
            return "D";
        }

    }
}
