package clone.rs.dungeon;

public class Game {
  final private String name;

  public Game(String name) {
    this.name = name;
  }

  public void run() {
    System.out.printf("Welcome to %s%n", this.name);
  }

  static void main(){
    Game game = new Game("RS clone dungeon");
    game.run();
  }
}
