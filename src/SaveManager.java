package src;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class SaveManager {
    private static final Path SAVE_FILE = Path.of(Paths.SAVE, "save.properties");

    public static void save(SaveData data) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("playerHealth", Integer.toString(data.playerHealth));
        properties.setProperty("playerDamage", Integer.toString(data.playerDamage));
        properties.setProperty("playerPotion", Integer.toString(data.playerPotion));
        properties.setProperty("playerMaxHealth", Integer.toString(data.playerMaxHealth));
        properties.setProperty("enemyHealth", Integer.toString(data.enemyHealth));
        properties.setProperty("enemyDamage", Integer.toString(data.enemyDamage));
        properties.setProperty("enemyPotion", Integer.toString(data.enemyPotion));
        properties.setProperty("enemyMaxHealth", Integer.toString(data.enemyMaxHealth));
        properties.setProperty("wins", Integer.toString(data.wins));

        Files.createDirectories(SAVE_FILE.getParent());
        try (Writer writer = Files.newBufferedWriter(SAVE_FILE)) {
            properties.store(writer, "Saved Match");
        }
    }

    public static SaveData load() throws Exception {
        if (!Files.exists(SAVE_FILE)) {
            return null;
        }

        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(SAVE_FILE)) {
            properties.load(reader);
        }

        SaveData data = new SaveData();
        data.playerHealth = getInt(properties, "playerHealth", 100);
        data.playerDamage = getInt(properties, "playerDamage", 10);
        data.playerPotion = getInt(properties, "playerPotion", 3);
        data.playerMaxHealth = getInt(properties, "playerMaxHealth", 100);
        data.enemyHealth = getInt(properties, "enemyHealth", 100);
        data.enemyDamage = getInt(properties, "enemyDamage", 10);
        data.enemyPotion = getInt(properties, "enemyPotion", 3);
        data.enemyMaxHealth = getInt(properties, "enemyMaxHealth", 100);
        data.wins = getInt(properties, "wins", 1);
        return data;
    }

    private static int getInt(Properties properties, String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key, Integer.toString(defaultValue)));
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }

    public static void saveWins(int win){
     SaveData saveData;
     try {
        saveData = load();
        if(!saveData.equals(null)){
            saveData.setWins(win);
            save(saveData);
        }
     } catch (Exception e) {
        e.printStackTrace();
     }
        
    } 
}