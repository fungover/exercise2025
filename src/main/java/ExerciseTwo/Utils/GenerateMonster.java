package ExerciseTwo.Utils;

import ExerciseTwo.Entities.Enemy.Enemies;
import ExerciseTwo.Entities.Enemy.Enemy;

public class GenerateMonster {

    public Enemy getEnemyToBattle() {

        Enemies enemies = new Enemies();
        int number = enemies.getNumEnemies();
        RandomGenerator random = new RandomGenerator();
        int randomNumber = random.getRandomNumber(number);

        return enemies.getEnemy(randomNumber);

    }
}
