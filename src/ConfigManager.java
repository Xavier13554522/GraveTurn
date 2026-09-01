package src;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigManager {
    private static final Path CONFIG_FILE = Path.of(Paths.CONFIG, "config.properties");

    public static void save(ConfigData data) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("masterVolume", Integer.toString(data.masterVolume));
        properties.setProperty("musicVolume", Integer.toString(data.musicVolume));
        properties.setProperty("effectsVolume", Integer.toString(data.effectsVolume));
        properties.setProperty("fullscreen", Boolean.toString(data.fullscreen));
        Files.createDirectories(CONFIG_FILE.getParent());
        try (Writer writer = Files.newBufferedWriter(CONFIG_FILE)) {
            properties.store(writer, "Game Config");
        }
    }

    public static ConfigData load() throws Exception {
        ConfigData data = new ConfigData();
        if (!Files.exists(CONFIG_FILE)) {
            save(data);
            return data;
        }

        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(CONFIG_FILE)) {
            properties.load(reader);
        }

        data.masterVolume = getInt(properties, "masterVolume", data.masterVolume);
        data.musicVolume = getInt(properties, "musicVolume", data.musicVolume);
        data.effectsVolume = getInt(properties, "effectsVolume", data.effectsVolume);
        data.fullscreen = Boolean.parseBoolean(
                properties.getProperty("fullscreen", Boolean.toString(data.fullscreen)));
                return data;
    }

    private static int getInt(Properties properties, String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key, Integer.toString(defaultValue)));
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }
}