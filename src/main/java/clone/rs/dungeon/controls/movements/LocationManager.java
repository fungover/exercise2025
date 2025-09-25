package clone.rs.dungeon.controls.movements;

import clone.rs.dungeon.character.Player;
import clone.rs.dungeon.locations.Falador;
import clone.rs.dungeon.locations.Lumbridge;
import clone.rs.dungeon.locations.Varrock;

import static clone.rs.dungeon.controls.Controls.scanner;

public class LocationManager {
  public static void changePlayerLocation(Player player, int locationChoice) {
    switch (locationChoice) {
      case 1 -> player.changeLocation(new Lumbridge());
      case 2 -> player.changeLocation(new Varrock());
      case 3 -> player.changeLocation(new Falador());
      default -> System.out.println("Invalid choice! Staying in current location.");
    }
  }
  public static void chooseLocation(Player player) {
    System.out.println("📍 Choose a location:");
    System.out.println("1️⃣  Lumbridge");
    System.out.println("2️⃣  Varrock");
    System.out.println("3️⃣  Dwarven Mine");
    int locationChoice = Integer.parseInt(scanner.nextLine());
    changePlayerLocation(player, locationChoice);
    System.out.println("You moved to " + player.getLocation());
    System.out.println(player.locationDescription());
  }
}
