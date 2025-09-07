package clone.rs.dungeon.weapons;

import clone.rs.dungeon.Items.Item;

public class Weapon {
  private String name;
  private double damage = 1;

  public Weapon(String name) {
    this.name = name;
  }

  public String name() { return name; }
  public double damage() {
    if(name.equalsIgnoreCase("Dragon crossbow")){
      return 16;
    } else if(name.equalsIgnoreCase("Rune longsword")){
      return 11;
    } else if(name.equalsIgnoreCase("Maple Bow")){
      return 4;
    }else if(name.equalsIgnoreCase("Bronze sword")){
      return 2;
    }else if(name.equalsIgnoreCase("Mithril longsword")){
      return 6;
    }else if(name.equalsIgnoreCase("Magic bow")){
      return 9;
    }
    return 1;
  }

  public void setName(String name) { this.name = name; }
  public void setDamage(double damage) { this.damage = damage; }
}