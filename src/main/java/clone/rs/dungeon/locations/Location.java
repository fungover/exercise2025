package clone.rs.dungeon.locations;

import clone.rs.dungeon.character.Enemy;
import clone.rs.dungeon.Items.Item;

public abstract class Location {
  public abstract String name();
  public abstract String description();
  public abstract Enemy enemy();
  public abstract Item item();
}
