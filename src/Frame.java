package src;

import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;

public class Frame extends JPanel {
    private final Character character;
    private final int frameWidth;
    private final int frameHeight;

    public Frame(Character character) {
        this.character = character;

        Image currentFrame = character.getCurrentFrame();
        this.frameWidth = (currentFrame != null ? currentFrame.getWidth(null) : 0) * 3;
        this.frameHeight = (currentFrame != null ? currentFrame.getHeight(null) : 0) * 3;

        this.setPreferredSize(new Dimension(Math.max(frameWidth, 1), Math.max(frameHeight, 1)));
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image frame = character.getCurrentFrame();
        if (frame != null) {
            int x = (getWidth() - frameWidth) / 2;
            int y = (getHeight() - frameHeight) / 2;
            g.drawImage(frame, x, y, frameWidth, frameHeight, this);
        }
    }
}
