package src;

import javax.swing.*;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

public class CreateWindow extends JFrame {
    private CardLayout containerCards = new CardLayout();
    private JPanel container = new JPanel(containerCards);
    private Map<String, JPanel> panels = new HashMap<>();
    
    public CreateWindow() {
        this.setIconImage(new ImageIcon(Paths.ASSETS + "ico.png").getImage());
        this.setTitle("Grave Turn-based Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(container);
        showPanel("Home");
        this.pack();
        this.setLocationRelativeTo(null); // Center the window
        this.setVisible(true);
    }
    public void showPanel(String name) {
        if (!panels.containsKey(name)) {
            if (name.equals("Home")) {
                PanelInitialize panel = new PanelInitialize(this);
                panels.put("Home", panel);
                container.add(panel, "Home");
            } else if (name.equals("Game")) {
                PanelGame panelGame = new PanelGame(this);
                panels.put("Game", panelGame);
                container.add(panelGame, "Game");
            }
        }
        if (panels.containsKey(name)) {
            containerCards.show(container, name);
            this.pack();
            this.setLocationRelativeTo(null);
        }
    }

    public void showPanel(JPanel panel, String name) {
        setPanel(panel, name);
        containerCards.show(container, name);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void setPanel(JPanel panel, String name) {
        if (panels.containsKey(name)) {
            container.remove(panels.get(name));
        }
        panels.put(name, panel);
        container.add(panel, name);
    }
}
