package src;

import javax.swing.*;

import java.awt.CardLayout;
public class CreateWindow extends JFrame {
    private CardLayout containerCards = new CardLayout();
    private JPanel container = new JPanel(containerCards);
    
    public CreateWindow() {
        this.setTitle("CAMINO A LA LOCURA");
        this.setSize(640, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the window
        this.setVisible(true);
        this.add(container);
        showPanel("Home");
    }
    public void showPanel(String name) {
        container.removeAll();
        container.revalidate();
        container.repaint();
        if (name.equals("Home")) {
            PanelInitialize panel = new PanelInitialize(this);
            container.add(panel, "Home");
        } else if (name.equals("Game")) {
            PanelGame panelGame = new PanelGame(this);
            container.add(panelGame, "Game");
        }
        containerCards.show(container, name);
    }
}
