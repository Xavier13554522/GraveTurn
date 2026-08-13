package src;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {
    public Button(String text, String pathImage) {
        super(text == null ? "" : text);
        this.setPreferredSize(new java.awt.Dimension(100, 80));
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setBackground(Color.GRAY);
        if (pathImage != null && !pathImage.isEmpty()) {
            ImageIcon icon = new ImageIcon(pathImage);
            Image scaledImage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            this.setIcon(new ImageIcon(scaledImage));
        }
        this.setForeground(Color.WHITE);
        this.setFocusable(false);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.GRAY);
            }
        });
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

    public void setIcon(String pathImage) {
        if (pathImage != null && !pathImage.isEmpty()) {
            ImageIcon icon = new ImageIcon(pathImage);
            Image scaledImage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            this.setIcon(new ImageIcon(scaledImage));
        } else {
            this.setIcon((javax.swing.Icon) null);
        }
    }
}
