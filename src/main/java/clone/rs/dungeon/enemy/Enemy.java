package clone.rs.dungeon.enemy;

public abstract class Enemy {
  public abstract String name();
  public abstract int health();
  public abstract int damage();
  public abstract int level();

  public void hit(){
    System.out.printf("%s hit! with %s%n", name(), damage());
  }

}
