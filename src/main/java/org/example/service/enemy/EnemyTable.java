package org.example.service.enemy;

import org.example.entities.enemies.Enemy;

public interface EnemyTable {
    /** Create a random enemy instance. */
    Enemy roll();
}
