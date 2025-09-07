package clone.rs.dungeon.locations;

import clone.rs.dungeon.character.Enemy;
import clone.rs.dungeon.Items.Item;

import java.util.Random;

public class Falador extends Location {
  @Override
  public String name() {
    return "Dwarven Mine";
  }

  @Override
  public String description() {
    return "(Hard)The Dwarven Mine contains almost every ore, with the exception of silver, runite, and special ores.";
  }

  @Override
  public Enemy enemy() {
    Random random = new Random();
    double chance = random.nextDouble();

    if (chance < 0.5) return new Enemy("\uD83D\uDC77\u200D♂\uFE0FDwarf", 10, 7, 4);
    else if (chance < 0.8) return new Enemy("\uD83E\uDD82Scorpion", 17, 14, 2);
    else if (chance < 0.95) return new Enemy("\uD83D\uDC77\u200D♂\uFE0F\uD83D\uDD2ADwarf gang member", 40, 44, 8);
    else return new Enemy("\uD83D\uDC51\uD83E\uDD82King Scorpion", 30, 32, 7);
  }

  @Override
  public Item item() {
    Random random = new Random();
    double chance = random.nextDouble();

    if (chance < 0.87) return new Item("Bones", 1, false, false, 0);
    else if (chance < 0.92) return new Item("Rune longsword", 1, true, false, 11);
    else if (chance < 0.98) return new Item("Monkfish", 1, false, true, 15);
    else return new Item("Dragon crossbow",1, true, false, 16);
  }

}
