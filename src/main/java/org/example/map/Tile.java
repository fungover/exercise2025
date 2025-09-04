package org.example.map;

//en ruta i dungeon-kartan, som vägg, fiende eller item. elr tomt
public class Tile {
private String tileType;

public Tile(String tileType) {
    this.tileType = tileType;
}

public String getTileType() {
    return tileType;
}

public boolean isWalkable() {
    return tileType.equals("Empty") || tileType.equals("Item") ;
}
}
