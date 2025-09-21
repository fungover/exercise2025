package map;

import entities.Enemy;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private String description;
    private Map<Integer, Room> exits;
    private Enemy enemy;

    public Room(String description) {
        this.description = description;
        this.exits = new HashMap<>();
    }

    public String getDescription() {
        return description;
    }

    public void addExit(int optionNumber, Room room) {
        exits.put(optionNumber, room);
    }

    public Room getExit(int optionNumber) {
        return exits.get(optionNumber);
    }

    public Map<Integer, Room> getExits() {
        return exits;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
