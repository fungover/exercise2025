package clone.rs.dungeon.Items;

public class Item {
  private String name;
  private int amount;

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

  public void addAmount(int amount) {
    this.amount += amount;
  }

  @Override
  public String toString() {
    return name + " x" + amount;
  }
}