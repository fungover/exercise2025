package clone.rs.dungeon.controls;

import clone.rs.dungeon.character.Player;
import clone.rs.dungeon.locations.Location;
import clone.rs.dungeon.locations.Lumbridge;
import clone.rs.dungeon.weapons.Hand;
import clone.rs.dungeon.weapons.Weapon;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SaveLoad {
    private static final String FILE_NAME = "player.yml";

    public static void savePlayer(Player player) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("name", player.getName());
        data.put("health", player.getHealth());
        data.put("level", player.getLevel());
        data.put("weapon", player.getWeapon());
        data.put("experience", player.getExp());
        data.put("location", player.getLocation());

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            yaml.dump(data, writer);
        }
    }

    public static Player loadPlayer() throws IOException {
        Yaml yaml = new Yaml(new LoaderOptions());
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Map<String, Object> data = yaml.load(reader);

            String name = (String) data.get("name");
            double health = ((Number) data.get("health")).doubleValue();
            double level = ((Number) data.get("level")).doubleValue();
            String weaponName = (String) data.get("weapon");
            int experience = ((Number) data.get("experience")).intValue();
            String  locationName = (String) data.get("location");

            Location location;
            if ("Lumbridge".equalsIgnoreCase(locationName)) {
                location = new Lumbridge();
            } else {
                location = new Lumbridge();
            }

            Weapon weapon;
            if ("Hand".equalsIgnoreCase(weaponName)) {
                weapon = new Hand();
            } else {
                weapon = new Hand();
            }


            Player player = new Player(name, health, level, weapon, location);
            player.addExperience(experience);
            return player;
        }
    }
}