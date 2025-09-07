package clone.rs.dungeon;

import java.io.IOException;

import static clone.rs.dungeon.controls.Controls.menu;

public class Game {
  final private String name;

  public Game(String name) {
    this.name = name;
  }

  public void run() throws IOException, InterruptedException {
    System.out.printf("Welcome to %s%n", this.name);
    menu();
  }

  static void main() throws IOException, InterruptedException {
    Game game = new Game("RS clone dungeon");
    game.run();
  }
}
