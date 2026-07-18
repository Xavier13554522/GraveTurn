package src;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;

public class LoadFrames {
    public static Map<Image[], Boolean> loadFrames(String basePath, int count, Boolean isOneTime, Boolean isTurn) {
        Map<Image[], Boolean> loadframesMap = new HashMap<>();
        Image[] frames = new Image[count];
        Boolean oneTime = isOneTime != null ? isOneTime : false;
        try {
            for (int i = 0; i < count; i++) {
                String path = basePath + "frame" + (i + 1) + ".png";
                File file = new File(path);
                if (!file.exists()) {
                    System.err.println("LoadFrames: archivo no encontrado -> " + file.getAbsolutePath());
                } else {
                    System.out.println("LoadFrames: cargando -> " + file.getAbsolutePath());
                }
                 BufferedImage originalImage = ImageIO.read(file);
                
                if (isTurn != null && isTurn && originalImage != null) {
                    AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                    tx.translate(-originalImage.getWidth(), 0); 
                    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    frames[i] = op.filter(originalImage, null);
                } else {
                    frames[i] = originalImage;
                }
            }
            loadframesMap.put(frames, oneTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadframesMap;
    }
}
