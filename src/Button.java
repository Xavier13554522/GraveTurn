package src;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {
    private Image backgroundImage;

    public Button(String text, String pathImage,String pathBgImage,Integer width,Integer height) {
        super(text == null ? "" : text);
        this.setPreferredSize(new Dimension(width == null ? 150 : width, height == null ? 50 : height));
        this.setFont(new Font("Arial", Font.BOLD, 18));
        setIcon(pathImage);
        setBackgroundImage(pathBgImage);
        this.setForeground(Color.WHITE);
        this.setFocusable(false);
    }
    public void setMouseEvent(MouseAdapter mouseEntered,MouseAdapter mouseExited) {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEntered.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseExited.mouseExited(e);
            }
        });
    }
    //fondo a los botones
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
            graphics.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(graphics);
    }
    public void setIcon(String pathImage) {
        if (pathImage != null && !pathImage.isEmpty()) {
            ImageIcon icon = new ImageIcon(pathImage);
            Image scaledImage = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            this.setIcon(new ImageIcon(scaledImage));
        } else {
            this.setIcon((javax.swing.Icon) null);
        }
    }
}
