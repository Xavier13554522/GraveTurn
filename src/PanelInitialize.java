package src;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.*;

public class PanelInitialize extends JPanel {
    private static final String BUTTON_PATH = Paths.BACKGROUND + "buttons/1/";
    private static final String NORMAL_BUTTON = BUTTON_PATH + "button.png";
    private static final String PRESSED_BUTTON = BUTTON_PATH + "press-button.png";
    private static final String ACTIVE_BUTTON = BUTTON_PATH + "active-button.png";

    public PanelInitialize(CreateWindow createWindow) {
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        AudioManager.getInstance().playBackgroundMusic("background");
        
        BackgroundPanel bg = new BackgroundPanel("background.png", 1280, 720);
        bg.setLayout(new GridBagLayout());
        // Objeto para dar las instrucciones de posición
        GridBagConstraints gbc = new GridBagConstraints();

        BackgroundPanel title = new BackgroundPanel("title.png", 550, 150);
        title.setOpaque(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 0, 0);
        bg.add(title, gbc);

        int btnwidth = 200;
        int btnheight = 60;
        // 2. CONFIGURACIÓN DEL BOTÓN (Fila 1)
        Button button = new Button("Play", null, NORMAL_BUTTON, btnwidth, btnheight);
        configureButtonStyle(button);
        button.addActionListener(e -> {
            button.setBackgroundImage(PRESSED_BUTTON);
            AudioManager.getInstance().stopMusic();
            createWindow.showPanel("Game");
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 40, 0);

        bg.add(button, gbc);

        Button buttonConfig = new Button("Config", null, NORMAL_BUTTON, btnwidth, btnheight);
        configureButtonStyle(buttonConfig);
        buttonConfig.addActionListener(e -> {
            buttonConfig.setBackgroundImage(PRESSED_BUTTON);
            createWindow.showPanel("Config");
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 40, 0);
        bg.add(buttonConfig, gbc);

        // 3. CONFIGURACIÓN DEL PANEL (Fila 2)
        Button buttonExit = new Button("Exit", null, NORMAL_BUTTON, btnwidth, btnheight);
        configureButtonStyle(buttonExit);
        buttonExit.addActionListener(e -> {
            buttonExit.setBackgroundImage(PRESSED_BUTTON);
            System.exit(0);
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);

        bg.add(buttonExit, gbc);
        this.add(bg, BorderLayout.CENTER);
    }

    private void configureButtonStyle(Button button) {
        button.setMouseEvent(
                () -> button.setBackgroundImage(ACTIVE_BUTTON),
                () -> button.setBackgroundImage(NORMAL_BUTTON));
    }
}
