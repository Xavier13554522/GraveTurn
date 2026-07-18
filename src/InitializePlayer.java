package src;
import java.awt.*;
import java.util.*;

public class InitializePlayer {
        public static Player initializePlayer(String characterName) {
                Map<Player.State, Map<Image[], Boolean>> playerFrames = new HashMap<>();
                playerFrames.put(Player.State.IDLE,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Idle/", 4, false,false));
                playerFrames.put(Player.State.DEAD,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Dead/", 6, true,false));
                playerFrames.put(Player.State.HURT,
                                LoadFrames.loadFrames(Paths.SelectCharacter(characterName) + "Hurt/", 3, true,false));

                return new Player(characterName, "A mysterious character with a dark past.", 100, 10,
                                new Animator<>(playerFrames, 150));
        }
}

