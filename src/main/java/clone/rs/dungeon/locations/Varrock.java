package clone.rs.dungeon.locations;

import clone.rs.dungeon.character.Enemy;
import clone.rs.dungeon.Items.Item;

import java.util.Random;

public class Varrock extends Location {
  @Override
  public String name() {
    return "Varrock";
  }

  @Override
  public String description() {
    return "(Medium)Varrock, originally known as Avarrocka, is the capital of the massive kingdom of Misthalin";
  }

  @Override
  public Enemy enemy() {
    Random random = new Random();
    double chance = random.nextDouble();

    if (chance < 0.5) return new Enemy("\uD83D\uDC00Giant rat", 5, 6, 1);
    else if (chance < 0.8) return new Enemy("\uD83D\uDC31Lucien", 14, 14, 2);
    else if (chance < 0.95) return new Enemy("\uD83D\uDC3BGrizzly bear", 27, 21, 4);
    else return new Enemy("⚔\uFE0F\uD83D\uDEE1\uFE0FBlack Knight", 42, 33, 6);
  }

  @Override
  public Item item() {
    Random random = new Random();
    double chance = random.nextDouble();

    if (chance < 0.75) return new Item("Bones", 1, false, false, 0);
    else if (chance < 0.85) return new Item("Mithril longsword", 1, true, false, 6);
    else if (chance < 0.95) return new Item("Swordfish", 1, false, true, 10);
    else return new Item("Magic bow", 1, true, false, 9);
  }
}
