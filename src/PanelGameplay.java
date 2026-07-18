package src;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;

class PanelGameplay extends JPanel {
    public PanelGameplay(Player player, Enemy enemy) {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(520, 220));
        this.setLayout(new GridLayout(1, 2, 20, 10));

        Frame frame = new Frame(player);
        this.add(frame);
        Frame frameEnemy = new Frame(enemy);
        this.add(frameEnemy);

        Timer timer = new Timer(100, e -> {
            player.update(100);
            enemy.update(100);
            frame.repaint();
            frameEnemy.repaint();
        });
        timer.start();
    }
}

class Frame extends JPanel {
    private final Character character;
    private final int frameWidth;
    private final int frameHeight;

    public Frame(Character character) {
        this.character = character;

        Image currentFrame = character.getCurrentFrame();
        this.frameWidth = (currentFrame != null ? currentFrame.getWidth(null) : 0) * 2;
        this.frameHeight = (currentFrame != null ? currentFrame.getHeight(null) : 0) * 2;

        this.setPreferredSize(new Dimension(Math.max(frameWidth, 1), Math.max(frameHeight, 1)));
        this.setBackground(Color.WHITE);
        this.setOpaque(true);
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
