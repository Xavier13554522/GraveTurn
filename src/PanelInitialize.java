package src;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class PanelInitialize extends JPanel {

    public PanelInitialize(CreateWindow createWindow) {
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        AudioManager.getInstance().setMusicVolume(0.6f);
        AudioManager.getInstance().setEffectsVolume(0.7f);
        AudioManager.getInstance().playBackgroundMusic("background");

        BackgroundPanel bg = new BackgroundPanel("background.png", 640, 480);
        bg.setLayout(new GridBagLayout());
        // Objeto para dar las instrucciones de posición
        GridBagConstraints gbc = new GridBagConstraints();

        // 2. CONFIGURACIÓN DEL BOTÓN (Fila 1)
        Button button = new Button("Play", null);
        button.addActionListener(e -> {
            AudioManager.getInstance().stopMusic();
            createWindow.showPanel("Game");
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 40, 0);

        bg.add(button, gbc);

        // 3. CONFIGURACIÓN DEL PANEL (Fila 2)
        Button buttonExit = new Button("Exit", null);
        buttonExit.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(buttonExit, "¿Estás seguro de que quieres salir?",
                    "Confirmar salida", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);

        bg.add(buttonExit, gbc);
        this.add(bg, BorderLayout.CENTER);
    }
}

class Button extends JButton {
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

class Label extends JLabel {
    public Label(String text) {
        super(text);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 24));
    }
}