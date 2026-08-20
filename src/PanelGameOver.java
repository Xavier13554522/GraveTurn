package src;

import java.awt.*;
import javax.swing.JPanel;

public class PanelGameOver extends JPanel {
    public PanelGameOver(CreateWindow createWindow, GameManager gameManager, Character winner, String winnerName) {
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(640, 480));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        LabelsComponents labelWinner = new LabelsComponents("Game Over! Winner:");
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        this.add(labelWinner, gbc);

        LabelsComponents labelWinnerName = new LabelsComponents(winnerName);
        gbc.gridy = 1;
        this.add(labelWinnerName, gbc);

        LabelsComponents labelStats = new LabelsComponents("Stats:");
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        this.add(labelStats, gbc);

        LabelsComponents labelStatsText = new LabelsComponents("Total turns: " + gameManager.getTurnCount());
        gbc.gridy = 3;
        this.add(labelStatsText, gbc);

        LabelsComponents labelWinnerStats = new LabelsComponents("Winner stats:");
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 0, 10, 0);
        this.add(labelWinnerStats, gbc);

        LabelsComponents labelAccDamage = new LabelsComponents("accDamage: " + winner.getAccDamage());
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 5, 0);
        this.add(labelAccDamage, gbc);

        LabelsComponents labelAccPotion = new LabelsComponents("accPotion: " + winner.getAccPotion());
        gbc.gridy = 6;
        this.add(labelAccPotion, gbc);

        LabelsComponents labelAccDodge = new LabelsComponents("accDodge: " + winner.getAccDodge());
        gbc.gridy = 7;
        this.add(labelAccDodge, gbc);

        LabelsComponents labelAccReceiveDamage = new LabelsComponents(
                "accReceiveDamage: " + winner.getAccReceiveDamage());
        gbc.gridy = 8;
        this.add(labelAccReceiveDamage, gbc);

        LabelsComponents labelCurrentHealth = new LabelsComponents("Health left: " + winner.getHealth());
        gbc.gridy = 9;
        this.add(labelCurrentHealth, gbc);

        Button buttonBack = new Button("Back", null, Paths.BACKGROUND + "buttons/1/button.png", 150, 50);
        buttonBack.addActionListener(e -> createWindow.showPanel("Home"));
        gbc.gridy = 10;
        gbc.insets = new Insets(20, 0, 0, 0);
        this.add(buttonBack, gbc);
    }

}
