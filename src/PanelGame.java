package src;

import java.awt.*;
import javax.swing.JPanel;

public class PanelGame extends JPanel {
        private final PanelGameplay gameplay;

        public PanelGame(CreateWindow createWindow) throws Exception {
                this.setBackground(Color.DARK_GRAY);
                this.setLayout(new GridBagLayout());
                AudioManager.getInstance().playBackgroundMusic("music-fight");
                GameManager gameManager = new GameManager(createWindow);
                
                Player player = InitializePlayer.initializePlayer("Black-Hat");
                Enemy enemy = InitializeEnemy.initializeEnemy("Black-Hat");

                SaveData saveData = SaveManager.load();
                if (saveData == null) {
                        saveData = new SaveData(player, enemy, 1);
                }
                SaveManager.save(saveData);
                GridBagConstraints gbc = new GridBagConstraints();

                gameplay = new PanelGameplay(player, enemy, gameManager);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 0, 0);
                this.add(gameplay, gbc);

                PanelActions panelActions = new PanelActions(
                                player, enemy, gameManager, gameplay.getEffectsManager());
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.insets = new Insets(0, 0, 0, 0);
                this.add(panelActions, gbc);
        }

        public void stopTimers() {
                gameplay.stopTimers();
        }
}
