package clone.rs.dungeon.Items;

public class Item {
  private String name;
  private int amount;
  private boolean wearable;
  private int effect;
  private boolean food;

  public Item(String name, int amount, boolean wearable, boolean food, int effect) {
    this.name = name;
  }

  public Item(String name) {
    this.name = name;
    this.amount = 1;
  }

  public Item(String name, int amount) {
    this.name = name;
    this.amount = amount;
  }

  public String getName() {
    return name;
  }

  public int getAmount() {
    return amount;
  }

  public boolean isWearable() {
    return wearable;
  }

  public int getEffect() {
    return effect;
  }

  public boolean isFood() {
    return food;
  }


  public void addAmount(int amount) {
    this.amount += amount;
  }

  @Override
  public String toString() {
    return name + " x" + amount;
  }
}