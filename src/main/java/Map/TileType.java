package Map;

public enum TileType {
    FLOOR(true), WALL(false);
    private final boolean walkable;
    TileType(boolean walkable) { this.walkable = walkable; }
    public boolean isWalkable() { return walkable; }
}
