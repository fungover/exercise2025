package dev.ronja.dungeon.map;

import dev.ronja.dungeon.entities.Enemy;

public class Tile {
    private TileType type;
    private Enemy enemy;

    public Tile(TileType type) { this.type = type; }
    public TileType getType() { return type; }
    public void setType(TileType type) { this.type = type; }

    public boolean isWalkable(){ return type.isWalkable() && enemy == null; }
    public char glyph() { return type.glyph(); }

   public Enemy getEnemy() { return enemy; }
    public void setEnemy(Enemy enemy) { this.enemy = enemy; }
    public boolean hasEnemy() { return enemy != null; }

}
