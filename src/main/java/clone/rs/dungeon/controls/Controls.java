package clone.rs.dungeon.controls;

import clone.rs.dungeon.character.Enemy;
import clone.rs.dungeon.character.Player;
import clone.rs.dungeon.locations.Falador;
import clone.rs.dungeon.locations.Lumbridge;
import clone.rs.dungeon.locations.Varrock;
import clone.rs.dungeon.weapons.Weapon;

import java.io.IOException;
import java.util.Scanner;

import static clone.rs.dungeon.controls.movements.LocationManager.changePlayerLocation;
import static clone.rs.dungeon.controls.movements.LocationManager.chooseLocation;

public class Controls {
  public static final Scanner scanner = new Scanner(System.in);

  public static void menu() throws IOException, InterruptedException {
    Player player = null;

    while (player == null) {
      System.out.printf("1. Create new character%n2. Load character%n3. Exit%n");
      String input = scanner.nextLine();

      switch (input) {
        case "1" -> {
          player = createCharacter();
          System.out.println("Your character " + player.getName() + " has been created.");
        }
        case "2" -> {
          player = SaveLoad.loadPlayer();
          if (player != null) {
            System.out.println("Loaded character: " + player.getName());
          } else {
            System.out.println("No saved character found.");
          }
        }
        case "3" -> {
          System.out.println("Goodbye!");
          return;
        }
        default -> System.out.println("Invalid choice!");
      }
    }

    while (true) {
      System.out.println("-----------");
      System.out.printf(
              "👤 %s  |  ❤️ %d/%d  |  ⭐ Lv.%d (%d XP)  |  ⚔️ %s  |  🏰 %s%n",
              player.getName(),
              (int) player.getHealth(), player.getMaxHp(),
              (int) player.getLevel(), (int) player.getExp(),
              player.getWeapon(),
              player.getLocation()
      );



      SaveLoad.savePlayer(player);
      System.out.printf(
              "%n"
                      + "1️⃣  Find enemy ⚔️%n"
                      + "2️⃣  Rest ❤️%n"
                      + "3️⃣  Change location 🏰%n"
                      + "4️⃣  Show inventory 🎒%n"
                      + "5️⃣  Equip item 🛡️%n"
                      + "6️⃣  Dragon Slayer Quest 🐉%n"
                      + "7️⃣  Exit 🚪%n"
      );
      String input2 = scanner.nextLine();

      switch (input2) {
        case "1" -> {
          Enemy enemy = player.getLocationObj().enemy();
          Combat.fight(player, enemy);
        }
        case "2" -> player.rest();
        case "3" -> {chooseLocation(player);}
        case "4" -> {
          if(player.showInventory(false, false)){
          }else System.out.println("🎒 Your inventory is empty!");
        }
        case "5" -> {
          if (player.showInventory(true, false)) {
            System.out.println("Enter item name: ");
            String item = scanner.nextLine();
            player.equipItem(item);
          } else
            System.out.println("👜 Your inventory is empty!");
        }
        case "6" -> {
          if(player.getLevel() < 10){
            System.out.println("⚔️ You need to be at least level 10 to do that!");
          }else Quest.start(player);
        }
        case "7" -> {
          System.out.println("Hurry back!");
          return;
        }
        default -> System.out.println("Invalid input.");
      }
    }
  }

  private static Player createCharacter() throws IOException {
    System.out.println("Enter character name:");
    String name = System.console().readLine();
    Player player = new Player( name, 10, 3, new Weapon("Hand"), new Lumbridge());
    SaveLoad.savePlayer(player);
    return player;
  }
}