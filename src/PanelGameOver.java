package src;

import java.awt.*;
import javax.swing.JPanel;

public class PanelGameOver extends JPanel {
    public PanelGameOver(CreateWindow createWindow, GameManager gameManager, String winner) {
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(640, 480));

        GridBagConstraints gbc = new GridBagConstraints();

        LabelsComponents labelWinner = new LabelsComponents("¡Juego terminado! Ganador: " + winner);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(labelWinner, gbc);

        Button buttonBack = new Button("Volver al menú", null);
        buttonBack.addActionListener(e -> createWindow.showPanel("Home"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(buttonBack, gbc);
    }

}
