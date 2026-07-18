package src;
import java.awt.*;
import java.util.*;

public class InitializeEnemy {
    public static Enemy initializeEnemy(String characterName) {
        Map<Enemy.State, Map<Image[], Boolean>> enemyFrames = new HashMap<>();
        enemyFrames.put(Enemy.State.IDLE,
                LoadFrames.loadFrames(Paths.SelectEnemy(characterName) + "Idle/", 4, false, true));
        enemyFrames.put(Enemy.State.DEAD,
                LoadFrames.loadFrames(Paths.SelectEnemy(characterName) + "Dead/", 6, true, true));
        enemyFrames.put(Enemy.State.HURT,
                LoadFrames.loadFrames(Paths.SelectEnemy(characterName) + "Hurt/", 3, true, true));

        return new Enemy(characterName, "A mysterious character with a dark past.", 100, 10,
                new Animator<>(enemyFrames, 150));
    }
}
