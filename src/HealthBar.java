package src;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.ImageIcon;

public class HealthBar extends JPanel {
    private static final int BAR_WIDTH = 400;
    private static final int BAR_HEIGHT = 30;
    private static final String HEALTH_BAR_DIR = Paths.UI + "healthBar/";
    private static final String BORDER_PATH = HEALTH_BAR_DIR + "healthBorder.png";
    private static final String BACKGROUND_PATH = HEALTH_BAR_DIR + "healthBg.png";
    private static final String FILL_PATH = HEALTH_BAR_DIR + "healthBarFill.png";

    private int maxHealth;
    private int currentHealth;

    private final Image borderImage;
    private final Image backgroundImage;
    private final Image fillImage;

    public HealthBar(int maxHealth) {
        this.maxHealth = Math.max(1, maxHealth);
        this.currentHealth = this.maxHealth;

        this.borderImage = loadImage(BORDER_PATH);
        this.backgroundImage = loadImage(BACKGROUND_PATH);
        this.fillImage = loadImage(FILL_PATH);

        setOpaque(false);
        setDoubleBuffered(true);

        Dimension barSize = new Dimension(BAR_WIDTH, BAR_HEIGHT);
        setPreferredSize(barSize);
        setMinimumSize(barSize);
        setMaximumSize(barSize);
    }
    public void updateHealth(int currentHealth, int maxHealth) {
        this.maxHealth = Math.max(1, maxHealth);
        this.currentHealth = Math.max(0, Math.min(currentHealth, this.maxHealth));
        repaint();
    }
    private Image loadImage(String path) {
        java.io.File file = new java.io.File(path);
        if (file.exists()) {
            return new ImageIcon(path).getImage();
        }
        return null;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = Math.max(1, maxHealth);
        this.currentHealth = Math.max(0, Math.min(this.currentHealth, this.maxHealth));
        repaint();
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.max(0, Math.min(currentHealth, maxHealth));
        repaint();
    }

    public void setHealth(int currentHealth, int maxHealth) {
        this.maxHealth = Math.max(1, maxHealth);
        this.currentHealth = Math.max(0, Math.min(currentHealth, this.maxHealth));
        repaint();
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public double getHealthRatio() {
        return maxHealth <= 0 ? 0 : (double) currentHealth / maxHealth;
    }

    private Insets getInnerInsets() {
        int width = getWidth();
        int height = getHeight();

        int horizontalInset = Math.max(10, width / 12);
        int verticalInset = Math.max(5, height / 12);
        return new Insets(verticalInset, horizontalInset, verticalInset, horizontalInset);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (g == null) {
            return;
        }

        Graphics2D graphics = (Graphics2D) g.create();
        try {
            int x = 0;
            int y = 0;
            int width = getWidth();
            int height = getHeight();

            if (width <= 0 || height <= 0) {
                return;
            }

            if (borderImage != null) {
                graphics.drawImage(borderImage, x, y, width, height, this);
            }

            Insets insets = getInnerInsets();
            int innerX = x + insets.left;
            int innerY = y + insets.top;
            int innerWidth = Math.max(1, width - insets.left - insets.right);
            int innerHeight = Math.max(1, height - insets.top - insets.bottom);

            int bgWidth = Math.max(1, innerWidth - 6);
            int bgHeight = Math.max(1, innerHeight - 2);
            int bgX = innerX + 3;
            int bgY = innerY + 1;

            if (backgroundImage != null) {
                graphics.drawImage(backgroundImage, bgX, bgY, bgWidth, bgHeight, this);
            }

            double ratio = getHealthRatio();
            int fillWidth = (int) Math.round((bgWidth - 2) * ratio);
            if (fillImage != null && fillWidth > 0) {
                graphics.drawImage(fillImage, bgX + 1, bgY + 1, fillWidth, bgHeight - 2, this);
            }

            String healthText = currentHealth + " / " + maxHealth;
            Font healthFont = getFont().deriveFont(Font.BOLD, 16f);
            FontMetrics fontMetrics = graphics.getFontMetrics(healthFont);
            int textX = x + (width - fontMetrics.stringWidth(healthText)) / 2;
            int textY = y + (height - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

            graphics.setFont(healthFont);
            graphics.setColor(Color.BLACK);
            graphics.drawString(healthText, textX - 1, textY);
            graphics.drawString(healthText, textX + 1, textY);
            graphics.drawString(healthText, textX, textY - 1);
            graphics.drawString(healthText, textX, textY + 1);
            graphics.setColor(Color.WHITE);
            graphics.drawString(healthText, textX, textY);
        } finally {
            graphics.dispose();
        }
    }
}
