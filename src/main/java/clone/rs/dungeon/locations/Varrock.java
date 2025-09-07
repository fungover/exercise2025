package clone.rs.dungeon.locations;

import clone.rs.dungeon.character.Enemy;

import java.util.Random;

public class Varrock extends Location {
  @Override
  public String name() {
    return "Varrock";
  }

  @Override
  public String description() {
    return "Varrock, originally known as Avarrocka, is the capital of the massive kingdom of Misthalin";
  }

  @Override
  public Enemy enemy() {
    Random random = new Random();
    double chance = random.nextDouble();

    if (chance < 0.5) return new Enemy("Giant rat", 5, 6, 1);
    else if (chance < 0.8) return new Enemy("Lucien", 14, 14, 2);
    else if (chance < 0.95) return new Enemy("Grizzly bear", 27, 21, 4);
    else return new Enemy("Black Knight", 42, 33, 6);
  }
}
