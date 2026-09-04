package src;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;

class PanelGameplay extends JPanel {
    private final ContainerCharacters containerCharacters;
    private final ContainerText containerText;

    public PanelGameplay(Player player, Enemy enemy, GameManager gameManager) {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(1280, 480));
        this.setLayout(new BorderLayout());
        containerText = new ContainerText(player, enemy);
        containerCharacters = new ContainerCharacters(player, enemy);
        containerCharacters.setOpaque(false);
        containerText.setOpaque(false);
        //
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setPreferredSize(new Dimension(1280, 100));
        container.setOpaque(false);
        LabelsComponents labelTurn = new LabelsComponents(
                "Turno: " + (gameManager.isPlayerTurn() ? "Jugador" : "Enemigo"));
        container.add(containerText, BorderLayout.SOUTH); 
        container.add(labelTurn, BorderLayout.CENTER);

        Timer timer = new Timer(100, e -> {
            labelTurn.setText("Turno: " + (gameManager.isPlayerTurn() ? "Jugador" : "Enemigo"));
        });
        timer.start();
        //
        BackgroundPanel backgroundPanel = new BackgroundPanel("midnight.png", 1280, 480);
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(container, BorderLayout.NORTH);
        backgroundPanel.add(containerCharacters, BorderLayout.CENTER);

        this.add(backgroundPanel, BorderLayout.CENTER);
    }

    public void stopTimers() {
        containerText.stopTimer();
        containerCharacters.stopTimer();
    }

    public EffectsManager getEffectsManager() {
        return containerCharacters.getEffectsManager();
    }
}
