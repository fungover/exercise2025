package service;

import entities.Player;
import map.DungeonMap;
import utils.Position;

public class Movement {
    private final Player player;
    private final DungeonMap dungeon;

    public Movement(Player player, DungeonMap dungeon) {
        this.player = player;
        this.dungeon = dungeon;
    }

    public void move() {
        Position playerPosition = player.getPosition();
    }
}
