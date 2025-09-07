package clone.rs.dungeon.character;

import clone.rs.dungeon.Items.Item;
import clone.rs.dungeon.locations.Location;
import clone.rs.dungeon.locations.Lumbridge;
import clone.rs.dungeon.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
  private List<Item> inventory = new ArrayList<>();
  private Weapon weapon;
  private Location location;
  private int experience = 0;

  public Player() {
    super();
    this.weapon = new Weapon("hand");
    this.location = new Lumbridge();
  }

  public Player(String name, double health, double level, Weapon weapon, Location location) {
    super(name, health, level);
    this.weapon = weapon;
    this.location = location;
  }

  public void checkLevel() {
    int expForNextLevel = (int) (level * level * 10);

    while (experience >= expForNextLevel) {
      experience -= expForNextLevel;
      level++;
      System.out.println("***Level up! You are now level " + level + "***");

      expForNextLevel = (int) (level * level * 10);
      health = getMaxHp();
    }
  }

  public void addExperience(int experience) {
    this.experience += experience;
    checkLevel();
  }

  public int getExp() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }

  public double hit() {
    return weapon.damage();
  }

  public String getWeapon() {
    return weapon.name();
  }

  public String getLocation() {
    return location.name();
  }

  public Location getLocationObj() {
    return location;
  }

  public Item getItem() {
    var item = location.item();
    System.out.println("You got: " + item);
    return item;
  }

  public void changeLocation(Location newLocation) {
    this.location = newLocation;
  }

  public String locationDescription() {
    return location.description();
  }

  public int getMaxHp() {
    int lvl = (int) getLevel();
    return 10 + (lvl - 3);
  }

  public void rest() throws InterruptedException {
    System.out.println("*** Resting restores your health ***");
    while (health < getMaxHp()) {
      System.out.println(getHealth() + "/" + getMaxHp());
      Thread.sleep(1000);
      health++;
    }
  }

  public void setWeapon(Weapon weapon) {
    this.weapon = weapon;
  }

  public void addItem(Item item) {
    for (Item i : inventory) {
      if (i.getName().equalsIgnoreCase(item.getName())) {
        i.addAmount(item.getAmount());
        return;
      }
    }
    inventory.add(item);
  }

  public List<Item> getInventory() {
    return inventory;
  }

  // if true, shows only wearable items
  public boolean showInventory(boolean isWearable, boolean isFood) {
    System.out.println("Inventory:");
    for (Item i : inventory) {
      if (isWearable && i.isWearable()) {
        System.out.println(i);
        return true;
      } else if (!isWearable && !isFood) {
        System.out.println(i);
        return true;
      } else if (isFood && i.isFood()) {
        System.out.println(i);
        return true;
      }
    }
    return false;
  }

  public void equipItem(String itemName) {
    for (Item i : inventory) {
      if (itemName.equalsIgnoreCase(i.getName())) {
        if (i.isWearable()) {
          Weapon newWeapon = new Weapon(i.getName());
          setWeapon(newWeapon);
          System.out.println("You equipped " + i.getName() + " with damage " + newWeapon.damage());
          return;
        } else {
          System.out.println(i.getName() + " is not wearable.");
          return;
        }
      }
    }
    System.out.println("You don't have that item in your inventory.");
  }

  public void useItem(String itemName) {
    for (int idx = 0; idx < inventory.size(); idx++) {
      Item i = inventory.get(idx);
      if (itemName.equalsIgnoreCase(i.getName())) {
        if (i.isFood()) {
          health += i.getEffect();
          System.out.println("You have eaten " + i.getName() + " and restored " + i.getEffect() + " HP.");

          i.addAmount(-1);
          if (i.getAmount() <= 0) {
            inventory.remove(idx);
          }

          return;
        } else {
          System.out.println(i.getName() + " is not eatable.");
          return;
        }
      }
    }
    System.out.println("You don't have that item in your inventory.");
  }

  public int checkItem(String itemName) {
    int quantity = 0;
    for (Item i : inventory) {
      if (itemName.equalsIgnoreCase(i.getName())) {
        quantity += i.getAmount();
      }
    }
    return quantity;
  }

}
