package src;

import java.awt.*;
import javax.swing.JPanel;

public class PanelGame extends JPanel {
        public PanelGame(CreateWindow createWindow) {
                this.setBackground(Color.DARK_GRAY);
                this.setLayout(new GridBagLayout());

                GameManager gameManager = new GameManager(createWindow);
                Player player = InitializePlayer.initializePlayer("Black-Hat");
                Enemy enemy = InitializeEnemy.initializeEnemy("Black-Hat");
                GridBagConstraints gbc = new GridBagConstraints();

                PanelGameplay windowGameplay = new PanelGameplay(player, enemy, gameManager);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 0, 0);
                this.add(windowGameplay, gbc);

                PanelActions panelActions = new PanelActions(player, enemy, gameManager);
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.insets = new Insets(0, 0, 20, 0);
                this.add(panelActions, gbc);
        }
}
