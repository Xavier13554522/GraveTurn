package src;

import java.awt.*;
import java.util.*;

public class InitializePlayer {
        public static Player initializePlayer(String characterName) {
                Map<Player.State, Map<Image[], Boolean>> playerFrames = new HashMap<>();
                playerFrames.put(Character.State.IDLE,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Idle/", 4, false, false));
                playerFrames.put(Character.State.ATTACK,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Attack/", 6, true,
                                                false));
                playerFrames.put(Character.State.HURT,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Hurt/", 4, true, false));
                playerFrames.put(Character.State.DODGE,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Dodge/", 3, true, false));
                playerFrames.put(Character.State.DEAD,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Dead/", 2, true, false));
                return new Player(characterName, "A mysterious character with a dark past.", 100, 10, 3,
                                new Animator<>(playerFrames, 150));
        }
}
