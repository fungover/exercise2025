package clone.rs.dungeon.locations;

public class Lumbridge extends Map{

  @Override
  public int size() {
    return width() * height();
  }

  @Override
  public int height() {
    return 10;
  }

  @Override
  public int width() {
    return 10;
  }

  @Override
  public String name() {
    return "Lumbridge";
  }

  @Override
  public String description() {
    return "Lumbridge is a large town where players begin their journey";
  }
}
