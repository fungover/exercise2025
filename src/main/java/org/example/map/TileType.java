package org.example.map;

/** Types of tiles present in the dungeon grid. */
public enum TileType {
    WALL,   // not walkable
    FLOOR,  // walkable
    SPAWN,  // player start (we'll set this to a tile inside the first room)
    BOSS,   // boss target (will be placed later)
    DOOR    // maybe add later
}
