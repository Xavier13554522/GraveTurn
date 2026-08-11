package src;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.ImageIcon;

public class PanelInitialize extends JPanel {

    public PanelInitialize(CreateWindow createWindow) {
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        AudioManager.getInstance().playBackgroundMusic("background");
        BackgroundPanel bg = new BackgroundPanel("midnight.png", 640, 480);
        bg.setLayout(new GridBagLayout());
        // Objeto para dar las instrucciones de posición
        GridBagConstraints gbc = new GridBagConstraints();

        // 1. CONFIGURACIÓN DEL LABEL (Fila 0)
        Label label = new Label("Grave Turn");
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 0; // Fila 0 (Arriba)
        gbc.insets = new Insets(0, 0, 20, 0);

        bg.add(label, gbc);

        // 2. CONFIGURACIÓN DEL BOTÓN (Fila 1)
        Button button = new Button("Play", null);

        button.addActionListener(e -> {
            AudioManager.getInstance().stopMusic();
            createWindow.showPanel("Game");
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);

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
        gbc.insets = new Insets(0, 0, 20, 0);

        bg.add(buttonExit, gbc);
        this.add(bg,BorderLayout.CENTER);
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