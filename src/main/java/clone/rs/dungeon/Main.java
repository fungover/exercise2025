package clone.rs.dungeon;

import java.io.IOException;

import static clone.rs.dungeon.controls.Controls.menu;

public class Main {
  final private String name;

  public Main(String name) {
    this.name = name;
  }

  public void run() throws IOException, InterruptedException {
    System.out.printf("Welcome to %s%n", this.name);
    menu();
  }

  public static void main(String[] args) {
    try {
      new Main("RS clone dungeon").run();
      } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      System.exit(1);
      }
    }
}
