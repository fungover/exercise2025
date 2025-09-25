package org.example.service.enemy;

import java.util.Random;
import java.util.function.Supplier;
import org.example.entities.enemies.Enemy;
import org.example.entities.enemies.Goblin;
import org.example.entities.enemies.Orc;
import org.example.utils.WeightedPicker;

public final class DefaultEnemyTable implements EnemyTable {
    private final Random random;

    // Pick which enemy type to spawn
    private final WeightedPicker<Supplier<Enemy>> enemyPicker =
            new WeightedPicker.Builder<Supplier<Enemy>>()
                    .add(Goblin::new, 65) // 65% goblin
                    .add(Orc::new,    35) // 35% orc
                    .build();

    public DefaultEnemyTable(long seed) {
        this.random = new Random(seed ^ 0xDEADBEEFCAFEL);
    }

    @Override
    public Enemy roll() {
        Supplier<Enemy> factory = enemyPicker.pick(random);
        return factory.get();
    }
}
