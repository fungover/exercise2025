package clone.rs.dungeon.weapons;

import clone.rs.dungeon.Items.Item;

public class Hand extends Weapon {

  @Override
  public String name() {
    return "Hand";
  }

  @Override
  public double damage() {
    return 1;
  }

  @Override
  public Item equipItem() {
    return null;
  }

  public static void getWeapon(){

  }
}


