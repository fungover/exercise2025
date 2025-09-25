package org.example.service;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.map.Dungeon;

public class MovementService {

    public boolean movePlayer(Player player, Direction direction, Dungeon dungeon) { // returns true if move was successful
        Position currentPos = player.getPosition(); // Get current position
        Position newPos = calculateNewPosition(currentPos, direction); // Calculate new position based on direction
        if (isValidMove(newPos, dungeon)) { // Check if the new position is valid
            player.setPosition(newPos); // Update player's position
            return true; // Move was successful
        }
        return false; // Move was not successful
    }

    private Position calculateNewPosition(Position currentPos, Direction direction) { // Calculate new position based on direction
        return switch (direction) { // Use switch with our direction enum
            case NORTH -> currentPos.moveNorth();
            case SOUTH -> currentPos.moveSouth();
            case EAST -> currentPos.moveEast();
            case WEST -> currentPos.moveWest();
        };
    }

    private boolean isValidMove(Position position, Dungeon dungeon) { // Check if the new position is within bounds and walkable
        var tile = dungeon.getTile(position.getX(), position.getY()); // Get the tile at the new position
        return tile != null && tile.isWalkable(); // Return true if tile exists and is walkable
    }
}
