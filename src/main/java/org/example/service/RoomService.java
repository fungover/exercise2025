package org.example.service;

import org.example.entities.Position;
import org.example.map.Room;
import org.example.map.TileType;

import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private final List<Room> rooms;
    private int currentRoomIndex;

    public RoomService() {
        this.rooms = new ArrayList<>();
        rooms.add(new Room("Dungeon Entrance", 8, 10));
        rooms.add(new Room("Treasure Room", 6, 8));
        rooms.add(new Room("Dragon's Lair", 10, 12));
        this.currentRoomIndex = 0;
    }

    public Room getCurrentRoom() {
        return rooms.get(currentRoomIndex);
    }

    public boolean isPlayerOnDoor(Position playerPos) {
        TileType currentTile = getCurrentRoom().getDungeon().getTile(playerPos.getX(), playerPos.getY()).getType();
        return currentTile == TileType.DOOR;
    }

    public String switchToNextRoom() {
        currentRoomIndex = (currentRoomIndex + 1) % rooms.size();
        return getCurrentRoom().getName();
    }

    public Position getStartingPosition() {
        return new Position(1, 1);
    }
}
