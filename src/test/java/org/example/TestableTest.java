package org.example;

import org.junit.jupiter.api.Test;

import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TestableTest {

    @Test
    public void createRandomEnemyAtReturnsAnDragonEnemy() {

        RandomGenerator randomGenerator = mock(RandomGenerator.class);

        var testable = new Testable(randomGenerator);
        var enemy = testable.createRandomEnemyAt(1, 1);
        assertThat(enemy).isInstanceOf(Enemy.Dragon.class);
    }

    @Test
    public void createRandomEnemyAtReturnsAnSpiderEnemy() {

        RandomGenerator randomGenerator = mock(RandomGenerator.class);
        when(randomGenerator.nextInt(anyInt())).thenReturn(1);
        var testable = new Testable(randomGenerator);
        var enemy = testable.createRandomEnemyAt(1, 1);
        assertThat(enemy).isInstanceOf(Enemy.Spider.class);
    }

}
