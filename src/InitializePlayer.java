package src;

import java.awt.*;
import java.util.*;

public class InitializePlayer {
        private static int playerHealth;
        private static int playerPotion;
        private static int playerDamage;

        public static Player initializePlayer(String characterName) {
                loadPlayerData();
                Map<Player.State, Map<Image[], Boolean>> playerFrames = new HashMap<>();
                playerFrames.put(Character.State.IDLE,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Idle/", 4, false, false));
                playerFrames.put(Character.State.ATTACK,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Attack/", 6, true,
                                                false));
                playerFrames.put(Character.State.HURT,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Hurt/", 4, true, false));
                playerFrames.put(Character.State.HEAL,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Heal/", 3, true, false));
                playerFrames.put(Character.State.DODGE,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Dodge/", 3, true, false));
                playerFrames.put(Character.State.DEAD,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Dead/", 2, true, false));
                return new Player(characterName, "A mysterious character with a dark past.", playerHealth, playerDamage,
                                playerPotion,
                                new Animator<>(playerFrames, 150));
        }

        private static void loadPlayerData() {
                try {
                        SaveData saveData = SaveManager.load();
                        if (saveData == null) {
                                playerHealth = 100;
                                playerPotion = 3;
                                playerDamage = 10;
                                return;
                        }
                        playerHealth = saveData.playerHealth;
                        playerDamage = saveData.playerDamage;
                        playerPotion = saveData.playerPotion;
                } catch (Exception e) {
                        playerHealth = 100;
                        playerPotion = 3;
                        playerDamage = 10;
                        e.printStackTrace();
                }
        }
}
