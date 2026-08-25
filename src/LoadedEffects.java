package src;

import java.awt.Image;
import java.io.File;
import java.util.Map;
import javax.imageio.ImageIO;

public class LoadedEffects {
    public static Image[] loadBloodFrames() {
        Map<Image[], Boolean> loaded = LoadFrames.loadFrames(
                Paths.SPRITES + "effects/blood/1/", 30, true, false);
        return loaded.keySet().iterator().next();
    }

    public static Image[] loadDodgeFrames() {
        Map<Image[], Boolean> loaded = LoadFrames.loadFrames(Paths.SPRITES + "effects/dodge/failed/", 6, true, false);
        return loaded.keySet().iterator().next();
    }

    public static Image[] loadSlashsPlayerFrames() {
        return loadFramesWithPrefix(Paths.SPRITES + "effects/slashs/2/player/", "frame", 14);
    }

    public static Image[] loadSlashsEnemyFrames() {
        return loadFramesWithPrefix(Paths.SPRITES + "effects/slashs/2/enemy/", "frame", 14);
    }

    public static Image[] loadSlashColor1Frames() {
        return loadFramesWithPrefix(Paths.SPRITES + "effects/slashs/1/1/", "frame", 9);
    }

    public static Image[] loadSlashColor2Frames() {
        return loadFramesWithPrefix(Paths.SPRITES + "effects/slashs/1/2/", "frame", 1);
    }

    public static Image[] loadSlashColor3Frames() {
        Map<Image[], Boolean> loaded = LoadFrames.loadFrames(Paths.SPRITES + "effects/slashs/1/3/", 9, true, true);
        return loaded.keySet().iterator().next();

    }

    private static Image[] loadFramesWithPrefix(String basePath, String prefix, int count) {
        Image[] frames = new Image[count];
        for (int index = 0; index < count; index++) {
            String path = basePath + prefix + (index + 1) + ".png";
            try {
                frames[index] = ImageIO.read(new File(path));
                if (frames[index] == null) {
                    System.err.println("LoadedEffects: imagen no válida -> " + path);
                }
            } catch (Exception exception) {
                System.err.println("LoadedEffects: no se pudo cargar -> " + path);
            }
        }
        return frames;
    }
}
