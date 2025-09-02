package org.example.game.core;

import org.example.entities.characters.Player;
import org.example.map.DungeonMap;

public record GameContext(DungeonMap map, Player player) {}
