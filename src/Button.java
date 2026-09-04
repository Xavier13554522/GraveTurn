package src;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Button extends JButton {
    private Image backgroundImage;
    private Integer width, height;
    private Image iconImage;
    private Color iconColor;

    public Button(String text, String pathImage, String pathBgImage, Integer width, Integer height) {
        super(text == null ? "" : text);
        this.width = width == null ? 150 : width;
        this.height = height == null ? 50 : height;
        this.setPreferredSize(new Dimension(width, height));
        this.setFont(new Font("Arial", Font.BOLD, 18));
        setIcon(pathImage);
        setBackgroundImage(pathBgImage);
        this.setForeground(Color.WHITE);
        this.setFocusable(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void setMouseEvent(Runnable mouseEntered, Runnable mouseExited) {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEntered.run();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseExited.run();
            }
        });
    }

    // fondo a los botones
    public void setBackgroundImage(String pathBgImage) {
        if (pathBgImage != null && !pathBgImage.isEmpty()) {
            ImageIcon bgIcon = new ImageIcon(pathBgImage);
            this.backgroundImage = bgIcon.getImage();
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            this.setOpaque(false);
        } else {
            this.backgroundImage = null;
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            this.setOpaque(false);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        if (backgroundImage != null) {
            graphics.drawImage(backgroundImage, 0, 0, width, height, this);
        }
        super.paintComponent(graphics);
    }

    public void setIcon(String pathImage) {
        if (pathImage != null && !pathImage.isEmpty()) {
            ImageIcon icon = new ImageIcon(pathImage);
            this.iconImage = icon.getImage();
            updateIcon();
        } else {
            this.iconImage = null;
            this.setIcon((javax.swing.Icon) null);
        }
    }

    public void setIconColor(Color color) {
        this.iconColor = color;
        updateIcon();
    }

    private void updateIcon() {
        if (iconImage == null) {
            this.setIcon((javax.swing.Icon) null);
            return;
        }

        BufferedImage renderedIcon = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = renderedIcon.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(iconImage, 0, 0, 64, 64, null);
        if (iconColor != null) {
            graphics.setComposite(AlphaComposite.SrcIn);
            graphics.setColor(iconColor);
            graphics.fillRect(0, 0, renderedIcon.getWidth(), renderedIcon.getHeight());
        }
        graphics.dispose();
        this.setIcon(new ImageIcon(renderedIcon));
    }

}
