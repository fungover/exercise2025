package clone.rs.dungeon.locations;

import clone.rs.dungeon.character.Enemy;

import java.util.Random;

public class Falador extends Location {
  @Override
  public String name() {
    return "Dwarven Mine";
  }

  @Override
  public String description() {
    return "The Dwarven Mine contains almost every ore, with the exception of silver, runite, and special ores.";
  }

  @Override
  public Enemy enemy() {
    Random random = new Random();
    double chance = random.nextDouble();

    if (chance < 0.5) return new Enemy("Dwarf", 10, 7, 4);
    else if (chance < 0.8) return new Enemy("Scorpion", 17, 14, 2);
    else if (chance < 0.95) return new Enemy("Dwarf gang member", 40, 44, 8);
    else return new Enemy("King Scorpion", 30, 32, 7);
  }
}
