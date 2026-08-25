package src;

import javax.swing.*;

import java.awt.CardLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.HashMap;
import java.util.Map;

public class CreateWindow extends JFrame {
    private CardLayout containerCards = new CardLayout();
    private JPanel container = new JPanel(containerCards);
    private Map<String, JPanel> panels = new HashMap<>();
    private boolean fullscreen;
    
    public CreateWindow() {
        loadConfig();
        this.setUndecorated(fullscreen);
        this.setIconImage(new ImageIcon(Paths.ASSETS + "ico.png").getImage());
        this.setTitle("Grave Turn-based Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(container);
        showPanel("Home");
        this.pack();
        applyWindowMode();
        this.setVisible(true);
        
    }

    private void loadConfig() {
        try {
            ConfigData config = ConfigManager.load();
            fullscreen = config.fullscreen;
            AudioManager audioManager = AudioManager.getInstance();
            audioManager.setMusicVolume(config.musicVolume / 100.0f);
            audioManager.setEffectsVolume(config.effectsVolume / 100.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyWindowMode() {
        if (fullscreen) {
            GraphicsDevice device = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice();
            device.setFullScreenWindow(this);

            for (DisplayMode supportedMode : device.getDisplayModes()) {
                if (supportedMode.getWidth() == 640 && supportedMode.getHeight() == 480) {
                    try {
                        device.setDisplayMode(supportedMode);
                    } catch (IllegalArgumentException exception) {
                        // El monitor no permite cambiar a esta profundidad de color.
                    }
                    break;
                }
            }
        } else {
            setExtendedState(JFrame.NORMAL);
            pack();
            setLocationRelativeTo(null);
        }
    }

    public void showPanel(String name){
        if (name.equals("Game") && panels.containsKey("Game")) {
            PanelGame previousGame = (PanelGame) panels.remove("Game");
            previousGame.stopTimers();
            container.remove(previousGame);
        }
        if (!panels.containsKey(name)) {
            if (name.equals("Home")) {
                PanelInitialize panel = new PanelInitialize(this);
                panels.put("Home", panel);
                container.add(panel, "Home");
            } else if (name.equals("Game")) {
                PanelGame panelGame = null;
                try {
                    panelGame = new PanelGame(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                panels.put("Game", panelGame);
                container.add(panelGame, "Game");
            }
        } else if (name.equals("Home")) {
            AudioManager.getInstance().playBackgroundMusic("background");
        }
        if (panels.containsKey(name)) {
            containerCards.show(container, name);
            applyWindowMode();
        }
    }

    public void showPanel(JPanel panel, String name) {
        setPanel(panel, name);
        containerCards.show(container, name);
        applyWindowMode();
        this.repaint();
    }

    public void setPanel(JPanel panel, String name) {
        if (panels.containsKey(name)) {
            container.remove(panels.get(name));
        }
        panels.put(name, panel);
        container.add(panel, name);
    }
}
