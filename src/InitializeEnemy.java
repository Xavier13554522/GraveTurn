package src;

import java.awt.*;
import java.util.*;

public class InitializeEnemy {
        private static int enemyHealth;
        private static int enemyPotion;
        private static int enemyDamage;

        public static Enemy initializeEnemy(String characterName) {
                loadEnemyData();
                Map<Enemy.State, Map<Image[], Boolean>> enemyFrames = new HashMap<>();
                enemyFrames.put(Character.State.IDLE,
                                LoadFrames.loadFrames(Paths.SelectEnemy(characterName) + "Idle/", 4, false, true));
                enemyFrames.put(Character.State.ATTACK,
                                LoadFrames.loadFrames(Paths.SelectEnemy(characterName) + "Attack/", 6, true,
                                                true));
                enemyFrames.put(Character.State.HURT,
                                LoadFrames.loadFrames(Paths.SelectEnemy(characterName) + "Hurt/", 4, true, true));
                enemyFrames.put(Character.State.HURT,
                                LoadFrames.loadFrames(Paths.SelectEnemy(characterName) + "Hurt/", 4, true, true));
                enemyFrames.put(Character.State.HEAL,
                                LoadFrames.loadFrames(Paths.SelectEnemy(characterName) + "Heal/", 3, true, true));
                enemyFrames.put(Character.State.DODGE,
                                LoadFrames.loadFrames(Paths.SelectEnemy(characterName) + "Dodge/", 3, true, true));
                enemyFrames.put(Character.State.DEAD,
                                LoadFrames.loadFrames(Paths.SelectEnemy(characterName) + "Dead/", 2, true, true));
                return new Enemy(characterName, "A mysterious character with a dark past.", enemyHealth, enemyDamage,
                                enemyPotion,
                                new Animator<>(enemyFrames, 150));
        }

        private static void loadEnemyData() {
                try {
                        SaveData saveData = SaveManager.load();
                        if (saveData == null) {
                                enemyHealth = 100;
                                enemyPotion = 3;
                                enemyDamage = 10;
                                return;
                        }
                        enemyHealth = saveData.enemyHealth;
                        enemyDamage = saveData.enemyDamage;
                        enemyPotion = saveData.enemyPotion;
                } catch (Exception e) {
                        enemyHealth = 100;
                        enemyPotion = 3;
                        enemyDamage = 10;
                        e.printStackTrace();
                }
        }
}
