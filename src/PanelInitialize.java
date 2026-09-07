package src;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;

public class PanelInitialize extends JPanel {
    public PanelInitialize(CreateWindow createWindow) {
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        AudioManager.getInstance().playBackgroundMusic("background");

        BackgroundPanel bg = new BackgroundPanel("background.png", 1280, 720);
        bg.setLayout(new BorderLayout());
        containerPanel container = new containerPanel(createWindow);
        JLabel versionLabel = new JLabel("v" + new ConfigData().version + "b by Xavier Gómez");
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        versionLabel.setForeground(Color.WHITE);
        bg.add(container, BorderLayout.CENTER);
        bg.add(versionLabel, BorderLayout.SOUTH);
        this.add(bg, BorderLayout.CENTER);
    }
}

class containerPanel extends JPanel {
    private static final String BUTTON_PATH = Paths.BACKGROUND + "buttons/1/";
    private static final String NORMAL_BUTTON = BUTTON_PATH + "button.png";
    private static final String PRESSED_BUTTON = BUTTON_PATH + "press-button.png";
    private static final String ACTIVE_BUTTON = BUTTON_PATH + "active-button.png";

    public containerPanel(CreateWindow createWindow) {
        this.setLayout(new GridBagLayout());
        BackgroundPanel title = new BackgroundPanel("title.png", 550, 150);
        title.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 0, 0);
        this.add(title, gbc);

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
        gbc.insets = new Insets(0, 0, 20, 0);

        this.add(button, gbc);

        Button buttonConfig = new Button("Config", null, NORMAL_BUTTON, btnwidth, btnheight);
        configureButtonStyle(buttonConfig);
        buttonConfig.addActionListener(e -> {
            buttonConfig.setBackgroundImage(PRESSED_BUTTON);
            createWindow.showPanel("Config");
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(buttonConfig, gbc);

        Button buttonTutorial = new Button("Tutorial", null, NORMAL_BUTTON, btnwidth, btnheight);
        configureButtonStyle(buttonTutorial);
        buttonTutorial.addActionListener(e -> {
            buttonTutorial.setBackgroundImage(PRESSED_BUTTON);
            createWindow.showPanel("Tutorial");
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(buttonTutorial, gbc);

        Button buttonExit = new Button("Exit", null, NORMAL_BUTTON, btnwidth, btnheight);
        configureButtonStyle(buttonExit);
        buttonExit.addActionListener(e -> {
            buttonExit.setBackgroundImage(PRESSED_BUTTON);
            System.exit(0);
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);

        this.add(buttonExit, gbc);
        this.setOpaque(false);
    }

    private void configureButtonStyle(Button button) {
        button.setMouseEvent(
                () -> button.setBackgroundImage(ACTIVE_BUTTON),
                () -> button.setBackgroundImage(NORMAL_BUTTON));
    }
}