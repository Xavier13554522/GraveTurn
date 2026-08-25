package src;

import java.awt.Graphics2D;
import java.awt.Image;

public class EffectInstance {
    private final Image[] frames;
    private final int centerX;
    private final int centerY;
    private final int width;
    private final int height;
    private final long frameDurationMillis;
    private int frameIndex;
    private long elapsedMillis;

    public EffectInstance(Image[] frames, int centerX, int centerY,
            long frameDurationMillis, double scale) {
        this.frames = frames;
        this.centerX = centerX;
        this.centerY = centerY;
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

    public void draw(Graphics2D graphics) {
        if (!isFinished()) {
            int x = centerX - width / 2;
            int y = centerY - height / 2;
            graphics.drawImage(frames[frameIndex], x, y, width, height, null);
        }
    }

    public boolean isFinished() {
        return frameIndex >= frames.length;
    }
}