package src;

import java.awt.*;
import java.util.*;

public class InitializeEnemy {
        public static Enemy initializeEnemy(String characterName) {
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
                return new Enemy(characterName, "A mysterious character with a dark past.", 100, 10, 3,
                                new Animator<>(enemyFrames, 150));
        }
}
