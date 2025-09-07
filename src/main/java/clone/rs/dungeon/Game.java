package clone.rs.dungeon;

import clone.rs.dungeon.character.Enemy;
import clone.rs.dungeon.character.Player;
import clone.rs.dungeon.weapons.Hand;
import clone.rs.dungeon.weapons.Weapon;

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
