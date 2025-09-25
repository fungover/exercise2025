package ExerciseTwo.Entities.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Enemies {

    List<Enemy> enemies = new ArrayList<>();

    public Enemies() {
        enemies.add(new Monster());
        enemies.add(new Troll());
        enemies.add(new Zombie());
    }

    public int getNumEnemies() {
        return enemies.size();
    }

    public Enemy getEnemy (int index) {
        return enemies.get(index);
    }

}
