package clone.rs.dungeon.locations;

import clone.rs.dungeon.character.Enemy;
import clone.rs.dungeon.Items.Item;

import java.util.Random;

public class Lumbridge extends Location{


  @Override
  public String name() {
    return "Lumbridge";
  }

  @Override
  public String description() {
    return "Lumbridge is a large town where players begin their journey";
  }

  @Override
  public Enemy enemy() {
    Random random = new Random();
    double chance = random.nextDouble();

    if (chance < 0.5) return new Enemy("Goblin", 5, 3, 1);
    else if (chance < 0.8) return new Enemy("Chicken", 3, 1, 0);
    else if (chance < 0.95) return new Enemy("Cow", 12, 8, 2);
    else return new Enemy("Guard", 20, 21, 3);
  }

  @Override
  public Item item() {
    Random random = new Random();
    double chance = random.nextDouble();

    if (chance < 0.95) return new Item("Bones", 1, false, false, 0);
    else if (chance < 0.97) return new Item("Bronze sword",1 , true, false, 2);
    else if (chance < 0.99) return new Item("Meat", 1, false, true, 5);
    else return new Item("Maple Bow",1, true, false, 4);
  }

}
