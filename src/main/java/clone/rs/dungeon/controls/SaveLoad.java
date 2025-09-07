package clone.rs.dungeon.controls;

import clone.rs.dungeon.character.Player;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

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

        try (FileWriter writer = new FileWriter("player.yml")) {
            yaml.dump(data, writer);
        }
    }

    public static Player loadPlayer() throws IOException {
        Yaml yaml = new Yaml(new Constructor(Player.class, new LoaderOptions()));
        try (FileReader reader = new FileReader(FILE_NAME)) {
            return yaml.load(reader);
        }
    }
}