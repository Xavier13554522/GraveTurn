package src;    

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackgroundPanel extends JPanel {
    private BufferedImage background;
    private int widthBg;
    private int heightBg;

    public BackgroundPanel(String bg, Integer widthBg, Integer heightBg) {
        try {
            String imagePath = bg;
            if (!bg.contains("/") && !bg.contains("\\")) {
                imagePath = Paths.SelectBackground(bg);
            }
            background = ImageIO.read(new File(imagePath));
            this.widthBg = widthBg != null ? widthBg : background.getWidth();
            this.heightBg = heightBg != null ? heightBg : background.getHeight();
            setPreferredSize(new Dimension(this.widthBg, this.heightBg));
            setOpaque(false);
        } catch (IOException e) {
            e.printStackTrace();
            this.widthBg = widthBg != null ? widthBg : 0;
            this.heightBg = heightBg != null ? heightBg : 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, widthBg, heightBg, this);
        }
    }
}
