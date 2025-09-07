package clone.rs.dungeon.weapons;

import clone.rs.dungeon.Items.Item;

public abstract class Weapon {
  public abstract String name();
  public abstract double damage();
  public abstract Item equipItem();
}
