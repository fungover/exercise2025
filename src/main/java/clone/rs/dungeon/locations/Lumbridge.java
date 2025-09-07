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
    return "(Easy)Lumbridge is a large town where players begin their journey";
  }

  @Override
  public Enemy enemy() {
    Random random = new Random();
    double chance = random.nextDouble();

    if (chance < 0.5) return new Enemy("\uD83D\uDC79Goblin", 5, 3, 1);
    else if (chance < 0.8) return new Enemy("\uD83D\uDC14Chicken", 3, 1, 0);
    else if (chance < 0.95) return new Enemy("\uD83D\uDC04Cow", 12, 8, 2);
    else return new Enemy("\uD83D\uDC77\u200D♂\uFE0F\uD83D\uDEE1\uFE0FGuard", 20, 21, 3);
  }

  @Override
  public Item item() {
    Random random = new Random();
    double chance = random.nextDouble();

    if (chance < 0.75) return new Item("Bones", 1, false, false, 0);
    else if (chance < 0.85) return new Item("Bronze sword",1 , true, false, 2);
    else if (chance < 0.95) return new Item("Meat", 1, false, true, 5);
    else return new Item("Maple Bow",1, true, false, 4);
  }

}
