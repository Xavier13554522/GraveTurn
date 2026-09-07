package src;    

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
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
        this.setOpaque(true);
        this.setBackground(Color.BLACK);

        try {
            String imagePath = bg;
            if (!bg.contains("/") && !bg.contains("\\")) {
                imagePath = Paths.SelectBackground(bg);
            }

            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                imageFile = new File(System.getProperty("user.dir"), imagePath);
            }
            if (!imageFile.exists()) {
                imageFile = new File(System.getProperty("user.dir"), "assets/" + bg);
            }

            background = ImageIO.read(imageFile);
            this.widthBg = widthBg != null ? widthBg : background.getWidth();
            this.heightBg = heightBg != null ? heightBg : background.getHeight();
            setPreferredSize(new Dimension(this.widthBg, this.heightBg));
        } catch (IOException e) {
            e.printStackTrace();
            this.widthBg = widthBg != null ? widthBg : 0;
            this.heightBg = heightBg != null ? heightBg : 0;
            setPreferredSize(new Dimension(this.widthBg, this.heightBg));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, widthBg, heightBg, this);
        } else {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
