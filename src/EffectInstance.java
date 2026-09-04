package src;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import javax.swing.SwingUtilities;

public class EffectInstance {
    private final Image[] frames;
    private final Component target;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;
    private final long frameDurationMillis;
    private int frameIndex;
    private long elapsedMillis;

    public EffectInstance(Image[] frames, Component target, int offsetX, int offsetY,
            long frameDurationMillis, double scale) {
        this.frames = frames;
        this.target = target;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.frameDurationMillis = frameDurationMillis;

        int originalWidth = frames[0].getWidth(null);
        int originalHeight = frames[0].getHeight(null);
        this.width = (int) (originalWidth * scale);
        this.height = (int) (originalHeight * scale);
    }

    public void update(long deltaMillis) {
        elapsedMillis += deltaMillis;

        while (elapsedMillis >= frameDurationMillis) {
            elapsedMillis -= frameDurationMillis;
            frameIndex++;
        }
    }

    public void draw(Graphics2D graphics, Component reference) {
        if (isFinished() || target == null || reference == null) {
            return;
        }

        Point point = SwingUtilities.convertPoint(target,
                target.getWidth() / 2 + offsetX,
                target.getHeight() / 2 + offsetY,
                reference);

        int x = point.x - width / 2;
        int y = point.y - height / 2;
        graphics.drawImage(frames[frameIndex], x, y, width, height, null);
    }

    public boolean isFinished() {
        return frameIndex >= frames.length;
    }
}